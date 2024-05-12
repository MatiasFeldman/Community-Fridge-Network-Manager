package ar.edu.utn.frba.dds.colaboraciones;

import ar.edu.utn.frba.dds.helpers.ConstanteMultiplicativa;

import java.time.LocalDate;

public class DonacionDeDinero implements ContribucionJuridica,ContribucionHumana{
    private LocalDate fechaDeDonacion;
    private Float monto;
    private Frecuencia frecuenciaDeDonacion;
    private boolean esPeriodica;

    @Override
    public void contribuir() {
        if(esPeriodica){
            frecuenciaDeDonacion.setFechaUltimaDonacion(fechaDeDonacion);
        }
        System.out.println("Donacion de dinero realizada: se han donado$" + monto);
    }

    @Override
    public double asignarPuntaje() {
        if(!esPeriodica){
            return monto * ConstanteMultiplicativa.CONSTANTE_PESOS_DONADOS;
        }else{
            return monto * frecuenciaDeDonacion.vecesCumplidas() * ConstanteMultiplicativa.CONSTANTE_PESOS_DONADOS;
        }
    }

}
