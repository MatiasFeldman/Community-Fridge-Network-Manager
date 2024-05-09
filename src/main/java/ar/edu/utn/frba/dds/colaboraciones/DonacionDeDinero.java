package ar.edu.utn.frba.dds.colaboraciones;

import java.time.LocalDate;

public class DonacionDeDinero implements ContribucionJuridica,ContribucionHumana{
    private LocalDate fechaDeDonacion;
    private Float monto;
    private Frecuencia frecuenciaDeDonacion;
    private boolean esPeriodica;

    @Override
    public void contribuir() {
        frecuenciaDeDonacion.setFechaUltimaDonacion(fechaDeDonacion);
    }

    @Override
    public double asignarPuntaje() {
        return frecuenciaDeDonacion.vecesCumplidas() * monto * 0.5; //TODO: hacer bien las constantes multiplicativas
    }
}
