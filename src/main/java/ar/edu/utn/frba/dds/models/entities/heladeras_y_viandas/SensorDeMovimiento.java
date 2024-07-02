package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import lombok.AllArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

@AllArgsConstructor
public class SensorDeMovimiento {
    private static String BROKER_URL;
    private String topic;
    private MqttClient client;

    public SensorDeMovimiento(String heladeraId) throws MqttException {
        this.topic = "heladera/" + heladeraId + "/movimiento";
        client = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client.connect();
    }

    public void enviarMovimiento(double temperatura) throws MqttException {
        String payload = String.valueOf(true);
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(1);
        client.publish(topic, message);
    }
}
