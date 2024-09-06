package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.exceptions.HeladeraInexistenteException;
import ar.edu.utn.frba.dds.exceptions.HeladeraSinReceptorException;
import ar.edu.utn.frba.dds.exceptions.UsuarioSinTarjetaException;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.Tarjeta;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaHumano;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TipoTarjeta;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.*;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.IntentoAperturaResuelto;
import ar.edu.utn.frba.dds.models.entities.helpers.conversor_json.ConversorJSON;
import ar.edu.utn.frba.dds.models.entities.helpers.json_to_entidad.JSONtoDenunciaFallaTecnica;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.models.repositories.intentos_de_apertura.IntentosDeAperturaRepository;
import ar.edu.utn.frba.dds.models.repositories.receptores_apertura_heladera.ReceptoresDeAperturaRepository;
import ar.edu.utn.frba.dds.models.repositories.solicitudes_de_apertura_de_heladera.SolicitudesDeAperturaRepository;
import ar.edu.utn.frba.dds.models.repositories.tarjetas.TarjetasRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class HeladerasController {
    private Accionador accionador;
    private SolicitudesDeAperturaRepository solicitudes;
    private IntentosDeAperturaRepository intentos;
    private TarjetasRepository tarjetas;
    private ReceptoresDeAperturaRepository receptoresDeApertura;
    private HeladerasRepository heladeras;


    public void reportarFallaTecnica(String json) {
        JsonNode node = ConversorJSON.convertir(json);

        String nombreHeladera = node.get("heladera").asText();
        if (heladeras.buscarPorNombre(nombreHeladera).isEmpty()) {
            throw new HeladeraInexistenteException("No se encontro la heladera");
        } else {
            DenunciaFallaTecnica denuncia = JSONtoDenunciaFallaTecnica.convertir(node);
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
        String rol = node.get("rol").asText();
        String heladera = node.get("heladera").asText();

        if (heladeras.buscarPorNombre(heladera).isEmpty()) {
            throw new HeladeraInexistenteException("No se encontro la heladera");
        }

        Heladera heladeraObj = heladeras.buscarPorNombre(heladera).get();

        Optional<MqttReceptorApertura> receptor = receptoresDeApertura.buscarReceptorDeHeladera(heladeraObj.getId());

        if (receptor.isEmpty()) {
            throw new HeladeraSinReceptorException("No se encontro el receptor de la heladera");
        }

        Optional<Tarjeta> posibleTarjeta = tarjetas.buscarTarjetaPorDuenio(idUsuario, TipoTarjeta.HUMANO);

        if (posibleTarjeta.isEmpty()) {
            throw new UsuarioSinTarjetaException("El usuario no tiene una tarjeta");
        }

        Tarjeta tarjeta = posibleTarjeta.get();

        SolicitudApertura solicitud = SolicitudApertura.create(fechaSoli, (TarjetaHumano) tarjeta, heladeraObj, cantViandas);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(solicitud);

        receptor.get().publicarSolicitudApertura(jsonMessage);

        solicitudes.guardar(solicitud);
    }


    public void registrarIntentoDeApertura(IntentoAperturaResuelto intento){
        intentos.guardar(intento);
    }

}
