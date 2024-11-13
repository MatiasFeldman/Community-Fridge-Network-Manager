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
    private static String BROKER_URL = "tcp://localhost:1883";
    private static String topic = "heladera/movimiento";
    private MqttClient client;
    private Long idHeladera;

    @SneakyThrows
    public SensorDeMovimiento(Long idHeladera){
        this.idHeladera = idHeladera;
        client = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client.connect();
        System.out.println("Sensor de movimiento de heladera " + idHeladera + " conectado");
    }

    public SensorDeMovimiento(Long idHeladera, String url) throws MqttException {
        BROKER_URL = url;
        this.idHeladera = idHeladera;
        client = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client.connect();
    }

    @SneakyThrows
    public void enviarMovimiento() {
        String jsonMessage = String.valueOf(this.idHeladera);
        MqttMessage message = new MqttMessage(jsonMessage.getBytes());
        message.setQos(1);
        message.setRetained(true); // Retener el mensaje hasta que el suscriptor lo reciba
        client.publish(topic, message);
        System.out.println("Mensaje de movimiento enviado para heladera con id: " + this.idHeladera);
    }

}
