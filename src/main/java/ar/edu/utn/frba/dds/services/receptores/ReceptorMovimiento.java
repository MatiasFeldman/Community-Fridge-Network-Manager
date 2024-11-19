package ar.edu.utn.frba.dds.services.receptores;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.sensores_y_receptores.MensajeSensorMovimiento;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Optional;

@Builder
public class ReceptorMovimiento implements IMqttMessageListener {
    private HeladerasRepository heladeras;
    private final String BROKER_URL = "ssl://8e252e51d75f43e39ab207604b518d35.s1.eu.hivemq.cloud:8883";


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
    private void suscribirseATopic(ReceptorMovimiento receptor) {
        String topic = "heladera/movimiento";

        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName("ddslanaranjamecanica");
        options.setPassword("U2yZtv,^T2xWxapQw}r>".toCharArray());

        MqttClient client = new MqttClient(BROKER_URL, "ReceptorMovimiento", new MemoryPersistence());

        client.connect(options);
        client.subscribe(topic, receptor);

        System.out.println("Receptor de movimiento conectado");

    }


    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) {
        Long idHeladera = Long.valueOf(mqttMessage.toString());
        System.out.println("Se detecto movimiento en la heladera con id: " + idHeladera);
        Optional<Heladera> posibleHeladera = heladeras.buscarPorId(idHeladera);
        posibleHeladera.ifPresent(h -> {
            h.hayMovimiento();
            ServiceLocator.instanceOf(HeladerasRepository.class).modificar(h);
        });
    }
}
