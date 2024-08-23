package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.IntentoAperturaResuelto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttReceptorHeladera implements IMqttMessageListener {
    private static String BROKER_URL;
    private static String topic_solicitudes = "heladeras/solicitudes_de_apertura";
    private static String topic_intentos = "heladeras/intentos_de_apertura";
    private MqttClient client_solicitudes;
    private MqttClient client_intentos;

    @SneakyThrows
    public MqttReceptorHeladera(String url){
        BROKER_URL = url;

        client_solicitudes = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client_solicitudes.connect();

        client_intentos = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client_intentos.subscribe(topic_intentos);
    }

    @SneakyThrows
    public MqttReceptorHeladera(){
        client_solicitudes = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client_solicitudes.connect();

        client_intentos = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client_intentos.subscribe(topic_intentos);
    }

    @SneakyThrows
    public void publicarSolicitudApertura(String json){
        MqttMessage message = new MqttMessage(json.getBytes());
        message.setQos(1);

        client_solicitudes.publish(topic_solicitudes, message);
    }


    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        String jsonMensaje = mqttMessage.toString();
        ObjectMapper mapper = new ObjectMapper();
        IntentoAperturaResuelto intento = mapper.readValue(jsonMensaje, IntentoAperturaResuelto.class);
    }
}
