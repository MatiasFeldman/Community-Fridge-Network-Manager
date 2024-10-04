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
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.intentos_de_apertura.IntentosDeAperturaRepository;
import ar.edu.utn.frba.dds.models.repositories.juridicas.JuridicasRepository;
import ar.edu.utn.frba.dds.models.repositories.solicitudes_de_apertura_de_heladera.SolicitudesDeAperturaRepository;
import ar.edu.utn.frba.dds.models.repositories.tarjetas_colaboradores.TarjetasColaboradoresRepository;
import ar.edu.utn.frba.dds.models.repositories.tarjetas_vulnerables.TarjetasVulnerablesRepository;
import ar.edu.utn.frba.dds.services.receptores.MqttReceptorApertura;
import ar.edu.utn.frba.dds.utils.direcciones.StringToDireccion;
import ar.edu.utn.frba.dds.utils.permisos.PermisoDenegadoException;
import ar.edu.utn.frba.dds.utils.server.CrudViewsHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class HeladerasController {
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

    public void suscribirseAHeladeras(Context context) {
        List<Heladera> heladeras = this.heladeras.buscarTodos();
        List<HeladeraOutputDTO> dtos = new ArrayList<>();

        heladeras.forEach(h -> {
            dtos.add(new HeladeraOutputDTO(h.getId(), h.getNombre(), h.direccionCompleta(), h.getCapActual(), h.getCapacidadMaxima(), h.getActiva(), h.getDireccion().getCoordenadas().getLongitud(), h.getDireccion().getCoordenadas().getLatitud()));
        });

        Map<String, Object> model = new HashMap<>(); // sirve para pasar parámetros a la vista
        model.put("heladeras", dtos);

        context.render("heladeras/mapa-de-heladeras-user.hbs", model);
    }

    public void create(Context context) {

    }

    public void editarHeladera(Context context) {
        List<Heladera> heladeras = this.heladeras.buscarTodos();
        List<HeladeraOutputDTO> dtos = new ArrayList<>();

        heladeras.forEach(h -> {
            dtos.add(new HeladeraOutputDTO(h.getId(), h.getNombre(), h.direccionCompleta(), h.getCapActual(), h.getCapacidadMaxima(), h.getActiva(), h.getDireccion().getCoordenadas().getLongitud(), h.getDireccion().getCoordenadas().getLatitud()));
        });

        Map<String, Object> model = new HashMap<>(); // sirve para pasar parámetros a la vista
        model.put("heladeras", dtos);

        context.render("heladeras/modificacion-heladera.hbs", model);
    }

    @SneakyThrows
    public void eliminarHeladera(Context ctx) {
        String body = ctx.body();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(body, Map.class);

        // Obtener el id del JSON
        Long heladeraId = Long.valueOf(jsonMap.get("id"));

        Optional<Heladera> heladera = heladeras.buscarPorId(heladeraId);
        if (heladera.isEmpty()) {
            ctx.status(HttpStatus.NOT_FOUND).result("Heladera no encontrada");
        } else {
            heladeras.eliminar(heladera.get());
            ctx.status(HttpStatus.OK).result("Heladera eliminada");
        }
    }

    @SneakyThrows
    public void modificarEstadoHeladera(Context ctx) {
        String body = ctx.body();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(body, Map.class);

        // Obtener el id del JSON
        Long heladeraId = Long.valueOf(jsonMap.get("id"));
        Boolean activa = Boolean.valueOf(jsonMap.get("activa"));

        Optional<Heladera> heladera = heladeras.buscarPorId(heladeraId);
        if (heladera.isEmpty()) {
            ctx.status(HttpStatus.NOT_FOUND).result("Heladera no encontrada");
        } else {
            heladera.get().setActiva(activa);
            heladeras.modificar(heladera.get());
            ctx.status(HttpStatus.OK).result("Heladera actualizada");
        }

    }

    @SneakyThrows
    public void actualizarHeladera(@NotNull Context ctx) {
        String body = ctx.body();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(body, Map.class);

        Long heladeraId = Long.valueOf(jsonMap.get("id"));
        String nombre = jsonMap.get("nombre");
        Integer capacidadMaxima = Integer.valueOf(jsonMap.get("capacidadMaxima"));
        Integer capacidadActual = Integer.valueOf(jsonMap.get("capacidadMaxima"));
        String direccionString = jsonMap.get("direccion");

        Direccion direccion = StringToDireccion.convertir(direccionString);

        Optional<Heladera> heladeraBuscada = heladeras.buscarPorId(heladeraId);
        if (heladeraBuscada.isEmpty()) {
            ctx.status(HttpStatus.NOT_FOUND).result("Heladera no encontrada");
        } else {
            Heladera heladera = heladeraBuscada.get();
            heladera.setNombre(nombre);
            heladera.setCapacidadMaxima(capacidadMaxima);
            heladera.setCapActual(capacidadActual);
            heladera.setDireccion(direccion);
            heladeras.modificar(heladera);
            ctx.status(HttpStatus.OK).result("Heladera actualizada");
        }

    }
}
