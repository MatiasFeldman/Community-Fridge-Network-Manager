package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

public class SensorDeMovimiento {
    private Heladera heladera;

    public SensorDeMovimiento(Heladera heladera) {
        this.heladera = heladera;
    }

    public void recibirMovimiento(){
        // recibe que hay movimiento y se lo manda a la heladera
    }
}
