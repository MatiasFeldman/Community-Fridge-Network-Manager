package ar.edu.utn.frba.dds.services.receptores;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.sensores_y_receptores.MensajeSensorMovimiento;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Optional;

@Builder
public class ReceptorMovimiento implements IMqttMessageListener {
    private HeladerasRepository heladeras;
    private static final String BROKER_URL = "";


    public ReceptorMovimiento create(HeladerasRepository heladeras) throws MqttException {
        ReceptorMovimiento receptor = ReceptorMovimiento
                .builder()
                .heladeras(heladeras)
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
        Optional<Heladera> posibleHeladera = heladeras.buscarPorId(mensaje.getIdHeladera());
        posibleHeladera.ifPresent(Heladera::hayMovimiento);
    }
}
