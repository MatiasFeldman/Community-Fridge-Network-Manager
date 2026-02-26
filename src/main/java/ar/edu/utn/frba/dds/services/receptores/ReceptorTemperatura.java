package ar.edu.utn.frba.dds.services.receptores;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.sensores_y_receptores.MensajeSensorTemperatura;
import ar.edu.utn.frba.dds.models.entities.helpers.conversor_json.ConversorJSON;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Optional;

@Setter
@Getter
@Builder
public class ReceptorTemperatura implements IMqttMessageListener {
    private final String BROKER_URL = "ssl://8e252e51d75f43e39ab207604b518d35.s1.eu.hivemq.cloud:8883";
    private HeladerasRepository heladeras;
    private MqttClient client;

    @SneakyThrows
    public static ReceptorTemperatura create(HeladerasRepository heladeras){
        ReceptorTemperatura receptor =  ReceptorTemperatura
                .builder()
                .heladeras(heladeras)
                .build();

        receptor.suscribirseATopic(receptor);
        return receptor;
    }

    private void suscribirseATopic(ReceptorTemperatura receptor){
        try{
            client = new MqttClient(BROKER_URL, "ReceptorTemperatura", new MemoryPersistence());


            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName("ddslanaranjamecanica");
            options.setPassword("U2yZtv,^T2xWxapQw}r>".toCharArray());

            client.connect(options);

            client.subscribe("heladera/temperatura", 1 ,receptor);

            System.out.println("Receptor de temperatura conectado");
        } catch (MqttException me){
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }

    }



    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonMessage = mqttMessage.toString();

        JsonNode json = ConversorJSON.convertir(jsonMessage);
        Double temperatura = Double.valueOf(json.get("temperatura").asText());
        Long idHeladera = Long.valueOf(json.get("idHeladera").asText());

        Optional<Heladera> posibleHeladera = heladeras.buscarPorId(idHeladera);

        posibleHeladera.ifPresent(h -> {
            h.evaluarTemperatura(temperatura);
            ServiceLocator.instanceOf(HeladerasRepository.class).modificar(h);
            System.out.println("Heladera actualizada en el repositorio");
        });
        if (!posibleHeladera.isPresent()){
            System.out.println("Heladera no encontrada");
        }
    }
}
