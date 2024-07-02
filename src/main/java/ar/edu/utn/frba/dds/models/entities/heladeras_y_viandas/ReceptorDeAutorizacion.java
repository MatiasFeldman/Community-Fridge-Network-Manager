package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ReceptorDeAutorizacion implements IMqttMessageListener {
    private Heladera heladera;
    private static String BROKER_URL;

    private void suscribirseATopic(ReceptorTemperatura receptor) throws MqttException {
        MqttClient client = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client.subscribe("heladera/" + heladera.getId().toString() + "/solicitudes", receptor);
    }
    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MensajeAutorizacionHeladera mensaje = mapper.readValue(mqttMessage.getPayload(), MensajeAutorizacionHeladera.class);

        Humano solicitante = mensaje.getSolicitante();
        boolean autorizacion = mensaje.getResultado();

        heladera.actualizarSolicitud(solicitante,autorizacion);
    }
}
