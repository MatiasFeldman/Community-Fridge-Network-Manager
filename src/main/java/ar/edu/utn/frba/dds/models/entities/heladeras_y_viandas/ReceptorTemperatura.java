package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import lombok.Setter;

import java.time.LocalDateTime;

@Setter
public class ReceptorTemperatura {
    private double tempMax;
    private double tempMin;
    private Accionador accionadorParaTemperatura;
    private LocalDateTime ultFechaRegistrada;

    public void evaluar(double temp){
        if (temp > tempMax || temp < tempMin){
            accionadorParaTemperatura.sucedeIncidente(TipoEvento.TEMPERATURA, LocalDateTime.now());
            setUltFechaRegistrada(LocalDateTime.now());
        }
    }

    public void evaluarConexion(){
        if (ultFechaRegistrada.plusMinutes(5).isBefore(LocalDateTime.now())){
            accionadorParaTemperatura.sucedeIncidente(TipoEvento.FALLA_CONEXION, LocalDateTime.now());
        }
    }

}
