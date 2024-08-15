package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.sensores_y_receptores;

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

@Setter
@Getter
@Builder
public class ReceptorTemperatura implements IMqttMessageListener {
    private static String BROKER_URL;
    private LocalDateTime ultFechaRegistrada;
    private Double temperaturaRegistrada;
    private UUID idHeladera;

    public ReceptorTemperatura create(UUID idHeladera) throws MqttException {
        ReceptorTemperatura receptor = ReceptorTemperatura
                .builder()
                .temperaturaRegistrada(null)
                .idHeladera(idHeladera)
                .ultFechaRegistrada(null)
                .build();

        receptor.suscribirseATopic(receptor);
        return receptor;
    }

    private void suscribirseATopic(ReceptorTemperatura receptor) throws MqttException {
        MqttClient client = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client.subscribe("heladera/temperatura", receptor);
    }


    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonMessage = new String(mqttMessage.getPayload());
        MensajeSensorTemperatura mensaje = mapper.readValue(jsonMessage, MensajeSensorTemperatura.class);
        if (mensaje.getIdHeladera().equals(idHeladera)) {
            setTemperaturaRegistrada(mensaje.getTemperatura());
            setUltFechaRegistrada(LocalDateTime.now());
        }

    }
}
