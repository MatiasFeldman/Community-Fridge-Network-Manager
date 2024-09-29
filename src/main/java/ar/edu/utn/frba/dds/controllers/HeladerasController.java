package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dtos.heladeras.HeladeraOutputDTO;
import ar.edu.utn.frba.dds.exceptions.HeladeraInexistenteException;
import ar.edu.utn.frba.dds.exceptions.UsuarioSinTarjetaException;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaColaborador;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.*;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.IntentoAperturaResuelto;
import ar.edu.utn.frba.dds.models.entities.helpers.conversor_json.ConversorJSON;
import ar.edu.utn.frba.dds.models.entities.helpers.json_to_entidad.JSONtoDenunciaFallaTecnica;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.intentos_de_apertura.IntentosDeAperturaRepository;
import ar.edu.utn.frba.dds.models.repositories.juridicas.JuridicasRepository;
import ar.edu.utn.frba.dds.models.repositories.solicitudes_de_apertura_de_heladera.SolicitudesDeAperturaRepository;
import ar.edu.utn.frba.dds.models.repositories.tarjetas_colaboradores.TarjetasColaboradoresRepository;
import ar.edu.utn.frba.dds.models.repositories.tarjetas_vulnerables.TarjetasVulnerablesRepository;
import ar.edu.utn.frba.dds.services.receptores.MqttReceptorApertura;
import ar.edu.utn.frba.dds.utils.permisos.PermisoDenegadoException;
import ar.edu.utn.frba.dds.utils.server.CrudViewsHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class HeladerasController implements CrudViewsHandler {
    private Accionador accionador;
    private SolicitudesDeAperturaRepository solicitudes;
    private IntentosDeAperturaRepository intentos;
    private TarjetasColaboradoresRepository tarjetas;
    private HeladerasRepository heladeras;
    private HumanosRepository humanos;
    private JuridicasRepository juridicas;


    public void reportarFallaTecnica(String json) {
        JsonNode node = ConversorJSON.convertir(json);
        Long idDenunciante = Long.parseLong(node.get("id_usuario").asText());
        String rol = node.get("rol").asText();
        Usuario usuario;

        if (Objects.equals(rol, "HUMANO")) usuario = humanos.buscarPorId(idDenunciante).get().getUser();
        else usuario = juridicas.buscarPorId(idDenunciante).get().getUser();

        String nombreHeladera = node.get("heladera").asText();
        if (heladeras.buscarPorNombre(nombreHeladera).isEmpty()) {
            throw new HeladeraInexistenteException("No se encontro la heladera");
        } else {
            DenunciaFallaTecnica denuncia = JSONtoDenunciaFallaTecnica.convertir(node, usuario);
            Heladera heladera = heladeras.buscarPorNombre(nombreHeladera).get();
            heladera.desactivar();

            denuncia.setHeladera(heladera);
            heladera.notificarFallaTecnica();

            accionador.sucedeFallaTecnica(denuncia, heladera);
        }


    }

    @SneakyThrows
    public void avisarApertura(String json) {
        JsonNode node = ConversorJSON.convertir(json);

        LocalDateTime fechaSoli = LocalDateTime.parse(node.get("fechaHoraSolicitud").asText());
        Integer cantViandas = node.get("cantidadDeViandas").asInt();
        Long idUsuario = Long.parseLong(node.get("id_usuario").asText());
        Long idTarjeta = Long.parseLong(node.get("id_tarjeta").asText());
        String rol = node.get("rol").asText();
        String heladera = node.get("heladera").asText();

        if (heladeras.buscarPorNombre(heladera).isEmpty()) {
            throw new HeladeraInexistenteException("No se encontro la heladera");
        }

        Heladera heladeraObj = heladeras.buscarPorNombre(heladera).get();

        MqttReceptorApertura receptor = new MqttReceptorApertura();

        if (!Objects.equals(rol, "HUMANO")) {
            throw new PermisoDenegadoException("No tiene permisos para realizar esta accion");
        }

        Optional<ColaboradorHumano> posibleHumano = humanos.buscarPorId(idUsuario);

        if (posibleHumano.isEmpty()) {
            throw new UsuarioSinTarjetaException("No se encontro el usuario");
        }

        TarjetaColaborador tarjeta = tarjetas.buscarPorId(idTarjeta).get();


        SolicitudApertura solicitud = SolicitudApertura.create(fechaSoli, tarjeta, heladeraObj, cantViandas);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(solicitud);

        receptor.publicarSolicitudApertura(jsonMessage);

        solicitudes.guardar(solicitud);
    }


    public void registrarIntentoDeApertura(IntentoAperturaResuelto intento) {
        intentos.guardar(intento);
    }

    @Override
    // Devolver todas las heladeras del sistema
    public void index(Context context) {
        List<Heladera> heladeras = this.heladeras.buscarTodos();
        List<HeladeraOutputDTO> dtos = new ArrayList<>();

        heladeras.forEach(h -> {
            dtos.add(new HeladeraOutputDTO(h.getNombre(), h.direccionCompleta(), h.getCapActual(), h.getCapacidadMaxima(), h.getActiva(), h.getDireccion().getCoordenadas().getLongitud(), h.getDireccion().getCoordenadas().getLatitud()));
        });

        Map<String, Object> model = new HashMap<>(); // sirve para pasar parámetros a la vista
        model.put("heladeras", heladeras);

        context.render("heladeras/heladeras.hbs", model);
    }

    public void suscribirseAHeladeras(Context context) {
        List<Heladera> heladeras = this.heladeras.buscarTodos();
        List<HeladeraOutputDTO> dtos = new ArrayList<>();

        heladeras.forEach(h -> {
            dtos.add(new HeladeraOutputDTO(h.getNombre(), h.direccionCompleta(), h.getCapActual(), h.getCapacidadMaxima(), h.getActiva(), h.getDireccion().getCoordenadas().getLongitud(), h.getDireccion().getCoordenadas().getLatitud()));
        });

        Map<String, Object> model = new HashMap<>(); // sirve para pasar parámetros a la vista
        model.put("heladeras", dtos);

        context.render("heladeras/mapa-de-heladeras-user.hbs", model);
    }

    @Override
    public void show(Context context) {
        Optional<Heladera> buscada = this.heladeras.buscarPorId(Long.parseLong(context.pathParam("id")));
        if (buscada.isEmpty()) {
            context.status(HttpStatus.NOT_FOUND);
            context.result("Heladera no encontrada");
        } else {
            Heladera h = buscada.get();
            HeladeraOutputDTO dto = new HeladeraOutputDTO(h.getNombre(), h.direccionCompleta(), h.getCapActual(), h.getCapacidadMaxima(), h.getActiva(), h.getDireccion().getCoordenadas().getLongitud(), h.getDireccion().getCoordenadas().getLatitud());

            Map<String, Object> model = new HashMap<>();
            model.put("heladera", dto);

            context.render("heladeras/heladera.hbs", model);
        }
    }


    @Override
    public void create(Context context) {

    }

    @Override
    public void save(Context context) {

    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) {

    }

    @Override
    public void delete(Context context) {

    }

}
