package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dtos.incidentes.DenunciaFallaTecnicaDTO;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.*;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.IntentoApertura;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.IntentoAperturaResuelto;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.MensajeSolicitudApertura;
import ar.edu.utn.frba.dds.models.repositories.intentos_de_apertura.IntentosDeAperturaRepository;
import ar.edu.utn.frba.dds.models.repositories.solicitudes_de_apertura_de_heladera.SolicitudesDeAperturaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class HeladerasController implements IMqttMessageListener {
    private Accionador accionador;
    private SolicitudesDeAperturaRepository solicitudes;
    private IntentosDeAperturaRepository intentos;
    private static String BROKER_URL;
    private static String topic_solicitudes = "heladeras/solicitudes_de_apertura";
    private static String topic_intentos = "heladeras/intentos_de_apertura";
    private MqttClient client_solicitudes;
    private MqttClient client_intentos;

    public HeladerasController(Accionador accionador, SolicitudesDeAperturaRepository solis, IntentosDeAperturaRepository intentos) throws MqttException {
        this.solicitudes = solis;
        this.intentos = intentos;
        this.accionador = accionador;
        client_solicitudes = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client_solicitudes.connect();
    }

    public HeladerasController(Accionador accionador, String url, SolicitudesDeAperturaRepository solis, IntentosDeAperturaRepository intentos) throws MqttException {
        this.solicitudes = solis;
        this.intentos = intentos;
        BROKER_URL = url;
        this.accionador = accionador;
        client_solicitudes = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client_solicitudes.connect();

        client_intentos = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client_intentos.subscribe(topic_intentos);
    }

    public void reportarFallaTecnica(Object solicitud){
        DenunciaFallaTecnicaDTO dto = (DenunciaFallaTecnicaDTO) solicitud;
        DenunciaFallaTecnica denuncia = DenunciaFallaTecnica.of(dto);

        accionador.registrarFallaTecnica(denuncia);
    }

    @SneakyThrows
    public void avisarApertura(Object solicitud){
        SolicitudApertura solicitudApertura = (SolicitudApertura) solicitud;

        MensajeSolicitudApertura msg = new MensajeSolicitudApertura(solicitudApertura.getIdHeladera(), solicitudApertura.getIdSolicitante() ,solicitudApertura.getFechaDeExpiracion());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(msg);

        MqttMessage message = new MqttMessage(jsonMessage.getBytes());
        message.setQos(1);

        client_solicitudes.publish(topic_solicitudes, message);

        solicitudes.guardar(solicitudApertura);
    }



    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        String jsonMensaje = mqttMessage.toString();
        ObjectMapper mapper = new ObjectMapper();
        IntentoAperturaResuelto intento = mapper.readValue(jsonMensaje, IntentoAperturaResuelto.class);

        intentos.guardar(intento);
    }
}
