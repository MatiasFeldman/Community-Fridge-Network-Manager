package ar.edu.utn.frba.dds.services.receptores;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.sensores_y_receptores.MensajeSensorMovimiento;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.*;

import java.util.Optional;

@Builder
public class ReceptorMovimiento implements IMqttMessageListener {
    private HeladerasRepository heladeras;
    private final String BROKER_URL = "tcp://localhost:1883";


    @SneakyThrows
    public static ReceptorMovimiento create(HeladerasRepository heladeras) {
        ReceptorMovimiento receptor = ReceptorMovimiento
                .builder()
                .heladeras(heladeras)
                .build();

        receptor.suscribirseATopic(receptor);
        return receptor;
    }

    @SneakyThrows
    private void suscribirseATopic(ReceptorMovimiento receptor){
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setKeepAliveInterval(10);

        MqttClient client = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client.connect(options);
        client.subscribe("heladera/movimiento", receptor);
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonMessage = new String(mqttMessage.getPayload());
        MensajeSensorMovimiento mensaje = mapper.readValue(jsonMessage, MensajeSensorMovimiento.class);
        System.out.println("Se detecto movimiento en la heladera con id: " + mensaje.getIdHeladera());
        Optional<Heladera> posibleHeladera = heladeras.buscarPorId(mensaje.getIdHeladera());
        posibleHeladera.ifPresent(Heladera::hayMovimiento);
    }
}
