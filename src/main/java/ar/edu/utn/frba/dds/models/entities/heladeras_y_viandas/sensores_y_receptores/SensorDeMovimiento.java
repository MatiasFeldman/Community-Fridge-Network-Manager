package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.sensores_y_receptores;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.UUID;

@AllArgsConstructor
public class SensorDeMovimiento {
    private static String BROKER_URL;
    private static String topic = "heladera/movimiento";
    private MqttClient client;
    private Long idHeladera;

    public SensorDeMovimiento(Long idHeladera) throws MqttException {
        this.idHeladera = idHeladera;
        client = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client.connect();
    }

    public SensorDeMovimiento(Long idHeladera, String url) throws MqttException {
        BROKER_URL = url;
        this.idHeladera = idHeladera;
        client = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client.connect();
    }

    @SneakyThrows
    public void enviarMovimiento() {
        ObjectMapper mapper = new ObjectMapper();
        String jsonMessage = mapper.writeValueAsString(new MensajeSensorMovimiento(this.idHeladera));
        MqttMessage message = new MqttMessage(jsonMessage.getBytes());
        message.setQos(1);
        client.publish(topic, message);
    }
}
