package ar.edu.utn.frba.dds.colaboraciones;

import ar.edu.utn.frba.dds.Heladeras_Y_Viandas.Heladera;

import java.time.LocalDate;

public class DistribucionViandas implements ContribucionHumana{
    private Heladera heladeraOrigen;
    private Heladera heladeraDestino;
    private int cantidadViandas;
    private String motivo;
    private LocalDate fechaDistribucion;


    @Override
    public void contribuir() {
        heladeraOrigen.quitarViandas(cantidadViandas);
        heladeraDestino.agregarViandas(cantidadViandas);
    }

    @Override
    public double asignarPuntaje() {
        return cantidadViandas * Reconocimiento.getInstance().getCteViandasDistribuidas();
    } //TODO: hacer bien las constantes multiplicativas
}
