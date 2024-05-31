package ar.edu.utn.frba.dds.colaboraciones;

import lombok.Getter;

import java.time.LocalDate;

public class DonacionDeDinero implements ContribucionJuridica,ContribucionHumana{
    private LocalDate fechaDeDonacion;
    @Getter
    private Float monto;
    private Frecuencia frecuenciaDeDonacion;
    private boolean esPeriodica;

    @Override
    public void contribuir(ColaboracionesRealizadas colaboracionesRealizadas) {
        if(esPeriodica){
            frecuenciaDeDonacion.setFechaUltimaDonacion(fechaDeDonacion);
        }
        System.out.println("Donacion de dinero realizada: se han donado$" + monto);
        colaboracionesRealizadas.agregarDonacionDeDinero(this);
    }

    public long vecesCumplidas(){
        if(!esPeriodica){
            return 1;
        }
        return frecuenciaDeDonacion.vecesCumplidas();
    }

    public double cantidadDonada(){
        return monto * vecesCumplidas();
    }



}
