package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.converter.FrecuenciaConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "donacion_de_dinero")
public class DonacionDeDinero extends Contribucion{

    @Column(name = "fecha_de_donacion")
    private LocalDate fechaDeDonacion;
    @Getter
    @Column(name = "monto")
    private Double monto;

    @Convert(converter = FrecuenciaConverter.class)
    @Column(name = "frecuencia")
    private Frecuencia frecuenciaDeDonacion;

    @Transient
    private Boolean esPeriodica;


    public static DonacionDeDinero of(Double monto, ChronoUnit unidad, Integer frecuencia){
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
