package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.time.LocalDateTime;

@Setter
@Builder
public class ReceptorTemperatura implements IMqttMessageListener {
    private static String BROKER_URL;
    private Accionador accionadorParaTemperatura;
    private LocalDateTime ultFechaRegistrada;
    private Heladera heladera;

    public ReceptorTemperatura create(Heladera heladera, Accionador accionadorParaTemperatura) throws MqttException {
        ReceptorTemperatura receptor = ReceptorTemperatura
                .builder()
                .heladera(heladera)
                .accionadorParaTemperatura(accionadorParaTemperatura)
                .ultFechaRegistrada(null)
                .build();

        receptor.suscribirseATopic(receptor);
        return receptor;
    }

    private void suscribirseATopic(ReceptorTemperatura receptor) throws MqttException {
        MqttClient client = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client.subscribe("heladera/" + heladera.getId().toString() + "/temperatura", receptor);
    }


    public void evaluar(double temp){
        if (temp > heladera.getTempMaxima() || temp < heladera.getTempMinima()){
            accionadorParaTemperatura.sucedeIncidente(TipoEvento.TEMPERATURA, LocalDateTime.now(), heladera);
            setUltFechaRegistrada(LocalDateTime.now());
        }
    }

    public void evaluarConexion(){
        if (ultFechaRegistrada.plusMinutes(5).isBefore(LocalDateTime.now())){
            accionadorParaTemperatura.sucedeIncidente(TipoEvento.FALLA_CONEXION, LocalDateTime.now(), heladera);
        }
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        this.evaluar(Double.parseDouble(mqttMessage.toString()));
    }
}
