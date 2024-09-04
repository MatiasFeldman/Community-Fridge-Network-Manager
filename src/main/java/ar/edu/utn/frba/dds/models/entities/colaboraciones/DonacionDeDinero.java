package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonacionDeDinero extends Contribucion{
    private LocalDate fechaDeDonacion;
    @Getter
    private double monto;
    private Frecuencia frecuenciaDeDonacion;
    private boolean esPeriodica;

    public DonacionDeDinero(Integer cant){
        this.monto = cant;
        this.esPeriodica = false;
    }


    public static DonacionDeDinero of(double monto, ChronoUnit unidad, Integer frecuencia){
        return DonacionDeDinero
                .builder()
                .monto(monto)
                .frecuenciaDeDonacion(new Frecuencia(unidad,frecuencia, LocalDate.now()))
                .esPeriodica(true)
                .fechaDeDonacion(LocalDate.now())
                .build();
    }

    public static DonacionDeDinero of(double monto){
        return DonacionDeDinero
                .builder()
                .monto(monto)
                .esPeriodica(false)
                .frecuenciaDeDonacion(null)
                .fechaDeDonacion(LocalDate.now())
                .build();
    }

    @Override
    public Double calcularPuntaje() {
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
