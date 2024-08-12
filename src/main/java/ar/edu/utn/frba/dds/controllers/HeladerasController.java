package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dtos.incidentes.DenunciaFallaTecnicaDTO;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.ACCION_APERTURA;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.*;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.IntentoApertura;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.MensajeSolicitudApertura;
import ar.edu.utn.frba.dds.models.repositories.solicitudes_de_apertura_de_heladera.SolicitudesDeAperturaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class HeladerasController {
    private Accionador accionador;
    private SolicitudesDeAperturaRepository solicitudes;
    private static String BROKER_URL;
    private static String TOPIC = "heladeras/solicitudes_de_apertura";
    private MqttClient client;

    public HeladerasController(Accionador accionador, SolicitudesDeAperturaRepository solis) throws MqttException {
        this.solicitudes = solis;
        this.accionador = accionador;
        client = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client.connect();
    }

    public HeladerasController(Accionador accionador, String url, SolicitudesDeAperturaRepository solis) throws MqttException {
        this.solicitudes = solis;
        BROKER_URL = url;
        this.accionador = accionador;
        client = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client.connect();
    }

    public void reportarFallaTecnica(Object solicitud){
        DenunciaFallaTecnicaDTO dto = (DenunciaFallaTecnicaDTO) solicitud;
        DenunciaFallaTecnica denuncia = DenunciaFallaTecnica.of(dto);

        accionador.registrarFallaTecnica(denuncia);
    }

    @SneakyThrows
    public void avisarApertura(Object solicitud){
        SolicitudApertura solicitudApertura = (SolicitudApertura) solicitud;

        MensajeSolicitudApertura msg = new MensajeSolicitudApertura(solicitudApertura.getIdHeladera(), solicitudApertura.getIdSolicitante() ,solicitudApertura.getFechaDeExpiracion(), ACCION_APERTURA.AVISO);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(msg);

        MqttMessage message = new MqttMessage(jsonMessage.getBytes());
        message.setQos(1);

        client.publish(TOPIC, message);

        solicitudes.guardar(solicitudApertura);
    }

    @SneakyThrows
    public void intentoApertura(Object solicitud){
        IntentoApertura intento = (IntentoApertura) solicitud;

        MensajeSolicitudApertura msg = new MensajeSolicitudApertura(intento.getIdHeladera(), intento.getIdTarjeta(), intento.getFechaHoraDeIntento(), ACCION_APERTURA.INTENTO);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(msg);

        MqttMessage message = new MqttMessage(jsonMessage.getBytes());
        message.setQos(1);

        client.publish(TOPIC, message);
    }

}
