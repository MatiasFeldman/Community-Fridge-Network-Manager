package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.converter.FrecuenciaConverter;
import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "donacion_de_dinero")
@Getter
public class DonacionDeDinero extends Persistente implements Contribucion{


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_humano", referencedColumnName = "id")
    private ColaboradorHumano colaboradorHumano;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_juridica", referencedColumnName = "id")
    private Juridica colaboradorJuridico;

    @Column(name = "fecha_de_donacion")
    private LocalDate fechaDeDonacion;


    @Column(name = "monto")
    private Double monto;

    @Convert(converter = FrecuenciaConverter.class)
    @Column(name = "frecuencia")
    private Frecuencia frecuenciaDeDonacion;

    @Transient
    private Boolean esPeriodica;


    public static DonacionDeDinero of(ColaboradorHumano colaboradorHumano, Double monto, ChronoUnit unidad, Integer frecuencia) {
        return DonacionDeDinero
                .builder()
                .monto(monto)
                .colaboradorHumano(colaboradorHumano)
                .frecuenciaDeDonacion(new Frecuencia(unidad, frecuencia, LocalDate.now()))
                .esPeriodica(true)
                .fechaDeDonacion(LocalDate.now())
                .build();
    }

    public static DonacionDeDinero of(ColaboradorHumano colaboradorHumano, double monto) {
        return DonacionDeDinero
                .builder()
                .monto(monto)
                .colaboradorHumano(colaboradorHumano)
                .esPeriodica(false)
                .frecuenciaDeDonacion(null)
                .fechaDeDonacion(LocalDate.now())
                .build();
    }

    public static DonacionDeDinero of(Juridica juridica, Double monto, ChronoUnit unidad, Integer frecuencia) {
        return DonacionDeDinero
                .builder()
                .monto(monto)
                .colaboradorJuridico(juridica)
                .frecuenciaDeDonacion(new Frecuencia(unidad, frecuencia, LocalDate.now()))
                .esPeriodica(true)
                .fechaDeDonacion(LocalDate.now())
                .build();
    }

    public static DonacionDeDinero of(Juridica juridica, double monto) {
        return DonacionDeDinero
                .builder()
                .monto(monto)
                .colaboradorJuridico(juridica)
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


    public long vecesCumplidas() {
        if (!esPeriodica) {
            return 1;
        }
        return frecuenciaDeDonacion.vecesCumplidas();
    }

    public double cantidadDonada() {
        return monto * vecesCumplidas();
    }


}
