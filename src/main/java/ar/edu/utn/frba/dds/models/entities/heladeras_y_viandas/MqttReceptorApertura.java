package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import ar.edu.utn.frba.dds.controllers.HeladerasController;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.IntentoAperturaResuelto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.UUID;

@NoArgsConstructor
@Setter
public class MqttReceptorApertura implements IMqttMessageListener {
    private static String BROKER_URL;
    private static String topic_solicitudes = "heladeras/solicitudes_de_apertura";
    private static String topic_intentos = "heladeras/intentos_de_apertura";
    private MqttClient client_solicitudes;
    private MqttClient client_intentos;
    private Boolean conectado = false;


    private static HeladerasController controller;

    @Getter
    private Heladera heladera;

    @SneakyThrows
    public MqttReceptorApertura(Heladera heladera){
        if (BROKER_URL != null){
            this.conectarseATopics();
            this.heladera = heladera;
        }

    }

    public void setController(HeladerasController c){
        controller = c;
    }

    public String getUrl(){
        return BROKER_URL;
    }

    public void setUrl(String url){
        if (url != null && this.conectado){
            this.desconectarseDeTopics();
            BROKER_URL = url;
            this.conectarseATopics();
        } else{
            BROKER_URL = url;
        }
    }


    @SneakyThrows
    public void conectarseATopics(){
        client_solicitudes = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client_solicitudes.connect();

        client_intentos = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client_intentos.subscribe(topic_intentos);

        this.conectado = true;
    }

    @SneakyThrows
    public void desconectarseDeTopics(){
        client_solicitudes.disconnect();
        client_intentos.unsubscribe(topic_intentos);

        this.conectado = false;
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

        controller.registrarIntentoDeApertura(intento);
    }

    public Long getIdHeladera(){
        return this.heladera.getId();
    }
}
