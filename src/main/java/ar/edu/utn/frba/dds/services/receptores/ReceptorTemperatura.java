package ar.edu.utn.frba.dds.services.receptores;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.sensores_y_receptores.MensajeSensorTemperatura;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Optional;

@Setter
@Getter
@Builder
public class ReceptorTemperatura implements IMqttMessageListener {
    private static String BROKER_URL = null;
    private HeladerasRepository heladeras;
    private MqttClient client;

    public ReceptorTemperatura create(HeladerasRepository heladeras) throws MqttException {
        ReceptorTemperatura receptor = ReceptorTemperatura
                .builder()
                .heladeras(heladeras)
                .build();

        if (BROKER_URL != null) receptor.suscribirseATopic(receptor);
        return receptor;
    }

    private void suscribirseATopic(ReceptorTemperatura receptor) throws MqttException {
        client = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client.subscribe("heladera/temperatura", receptor);
    }

    @SneakyThrows
    public void setBrokerUrl(String url){
        if (BROKER_URL == null){
            BROKER_URL = url;
            this.suscribirseATopic(this);
        }
        else {
            BROKER_URL = url;
            this.cambioDeBroker();
        }
    }

    @SneakyThrows
    public void cambioDeBroker(){
        client.unsubscribe("heladera/temperatura");
        client = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client.subscribe("heladera/temperatura", this);
    }


    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonMessage = new String(mqttMessage.getPayload());
        MensajeSensorTemperatura mensaje = mapper.readValue(jsonMessage, MensajeSensorTemperatura.class);
        Optional<Heladera> posibleHeladera = heladeras.buscarPorId(mensaje.getIdHeladera());
        posibleHeladera.ifPresent(h -> h.evaluarTemperatura(mensaje.getTemperatura()));
    }
}
