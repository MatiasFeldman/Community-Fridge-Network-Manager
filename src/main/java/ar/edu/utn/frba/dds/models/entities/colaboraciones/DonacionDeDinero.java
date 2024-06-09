package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
public class DonacionDeDinero implements ContribucionJuridica,ContribucionHumana{
    private LocalDate fechaDeDonacion;
    @Getter
    private double monto;
    private Frecuencia frecuenciaDeDonacion;
    private boolean esPeriodica;

    public DonacionDeDinero(Integer cant){
        this.monto = cant;
        this.esPeriodica = false;
    }

    @Override
    public void contribuir() {
        if(esPeriodica){
            frecuenciaDeDonacion.setFechaUltimaDonacion(fechaDeDonacion);
        }
        System.out.println("Donacion de dinero realizada: se han donado$" + monto);
    }

    @Override
    public double calcularPuntaje() {
        ConstantesMultiplicativas constantes = new ConstantesMultiplicativas();
        return this.cantidadDonada() * constantes.getCtePesosDonados();
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
