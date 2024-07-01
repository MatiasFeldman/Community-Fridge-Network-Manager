package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import lombok.Builder;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.time.LocalDateTime;

@Builder
public class ReceptorMovimiento implements IMqttMessageListener {
    private Accionador accionadorParaMovimiento;
    private Heladera heladera;
    private static String BROKER_URL;


    public ReceptorMovimiento create(Heladera heladera, Accionador accionadorParaMovimiento) throws MqttException {
        ReceptorMovimiento receptor = ReceptorMovimiento
                .builder()
                .heladera(heladera)
                .accionadorParaMovimiento(accionadorParaMovimiento)
                .build();

        receptor.suscribirseATopic(receptor);
        return receptor;
    }

    private void suscribirseATopic(ReceptorMovimiento receptor) throws MqttException {
        MqttClient client = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client.subscribe("heladera/" + heladera.getId().toString() + "/movimiento", receptor);
    }

    public void evaluar(boolean movimiento){
        if (movimiento){
            accionadorParaMovimiento.sucedeIncidente(TipoEvento.MOVIMIENTO, LocalDateTime.now(), heladera);
        }
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        evaluar(true);
    }
}
