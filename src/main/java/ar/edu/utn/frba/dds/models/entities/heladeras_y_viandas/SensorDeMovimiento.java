package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SensorDeMovimiento {
    private ReceptorMovimiento receptor;

    public void recibirMovimiento(String string){
        // recibe que hay movimiento y se lo manda a la heladera
    }
}
