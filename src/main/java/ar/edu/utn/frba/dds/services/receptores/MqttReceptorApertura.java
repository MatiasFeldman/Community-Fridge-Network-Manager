package ar.edu.utn.frba.dds.services.receptores;

import ar.edu.utn.frba.dds.controllers.HeladerasController;
import ar.edu.utn.frba.dds.exceptions.HeladeraInexistenteException;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.SolicitudApertura;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.IntentoAperturaResuelto;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Optional;

@NoArgsConstructor
@Setter
public class MqttReceptorApertura implements IMqttMessageListener {
    private static String BROKER_URL = "tcp://broker.hivemq.com:1883";
    private static String topic_solicitudes = "heladeras/solicitudes_de_apertura";
    private static String topic_intentos = "heladeras/intentos_de_apertura";
    private MqttClient client_solicitudes;
    private MqttClient client_intentos;
    private Boolean conectado = false;

    private HeladerasRepository heladeras;
    private static HeladerasController controller;

    @SneakyThrows
    public MqttReceptorApertura(Heladera heladera){
        if (BROKER_URL != null){
            this.conectarseATopics();
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
        ObjectMapper mapper = new ObjectMapper();
        SolicitudApertura solicitud = mapper.readValue(json, SolicitudApertura.class);

        Optional<Heladera> posibleHeladera = this.buscarHeladeraDestino(solicitud.getIdHeladera());
        if (posibleHeladera.isPresent()){
            posibleHeladera.get().agregarSolicitudApertura(solicitud);
        } else{
            throw new HeladeraInexistenteException("No se encontro la heladera");
        }

    }


    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        String jsonMensaje = mqttMessage.toString();
        ObjectMapper mapper = new ObjectMapper();
        IntentoAperturaResuelto intento = mapper.readValue(jsonMensaje, IntentoAperturaResuelto.class);

        controller.registrarIntentoDeApertura(intento);
    }

    public Optional<Heladera> buscarHeladeraDestino(Long id){
        return this.heladeras.buscarPorId(id);
    }
}
