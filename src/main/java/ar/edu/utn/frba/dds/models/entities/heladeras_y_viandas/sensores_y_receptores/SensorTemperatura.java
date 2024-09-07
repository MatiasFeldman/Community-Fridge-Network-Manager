package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.sensores_y_receptores;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.UUID;

public class SensorTemperatura {
    private static String BROKER_URL;
    private String topic;
    private MqttClient client;
    private Long idHeladera;

    public SensorTemperatura(UUID idHeladera) throws MqttException {
        this.topic = "heladera/temperatura";
        client = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client.connect();
    }

    @SneakyThrows
    public void enviarTemperatura(double temperatura) throws MqttException {
        ObjectMapper mapper = new ObjectMapper();
        String JSONmensaje = mapper.writeValueAsString(new MensajeSensorTemperatura(temperatura, idHeladera));
        MqttMessage message = new MqttMessage(JSONmensaje.getBytes());
        message.setQos(1);
        client.publish(topic, message);
    }
}