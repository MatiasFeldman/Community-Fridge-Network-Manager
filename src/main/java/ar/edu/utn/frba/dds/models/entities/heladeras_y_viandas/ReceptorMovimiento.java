package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public class ReceptorMovimiento implements IMqttMessageListener {
    private UUID idHeladera;
    private static String BROKER_URL;
    @Setter
    @Getter
    private boolean movimiento = false;


    public ReceptorMovimiento create(UUID idHeladera) throws MqttException {
        ReceptorMovimiento receptor = ReceptorMovimiento
                .builder()
                .idHeladera(idHeladera)
                .build();

        receptor.suscribirseATopic(receptor);
        return receptor;
    }

    private void suscribirseATopic(ReceptorMovimiento receptor) throws MqttException {
        MqttClient client = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client.subscribe("heladera/movimiento", receptor);
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonMessage = new String(mqttMessage.getPayload());
        MensajeSensorMovimiento mensaje = mapper.readValue(jsonMessage, MensajeSensorMovimiento.class);
        if (mensaje.getIdHeladera().equals(idHeladera)) {
            setMovimiento(true);
        }
    }
}
