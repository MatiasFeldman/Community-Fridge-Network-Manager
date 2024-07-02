package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import lombok.AllArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SensorTemperatura {
    private static String BROKER_URL;
    private String topic;
    private MqttClient client;

    public SensorTemperatura(String heladeraId) throws MqttException {
        this.topic = "heladera/" + heladeraId + "/temperatura";
        client = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client.connect();
    }

    public void enviarTemperatura(double temperatura) throws MqttException {
        String payload = String.format("%.2f", temperatura);
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(1);
        client.publish(topic, message);
    }
}