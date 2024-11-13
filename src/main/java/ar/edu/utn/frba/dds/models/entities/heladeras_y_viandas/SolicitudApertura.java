package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaColaborador;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.MotivoApertura;
import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "solicitud_de_apertura")
public class SolicitudApertura extends Persistente {

    @Column(name = "fecha_hora_solicitud", nullable = false)
    private LocalDateTime fechaHoraSolicitud;

    @Transient
    private static Integer horasParaEjecutarAccion = 3;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tarjeta", referencedColumnName = "id")
    private TarjetaColaborador solicitante;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_heladera", referencedColumnName = "id")
    private Heladera heladera;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_colaborador", referencedColumnName = "id")
    private ColaboradorHumano colaboradorHumano;

    @Column(name = "cantidad_viandas", nullable = false)
    private Integer cantidadDeViandas;

    @Column(name = "fecha_expiracion", nullable = false)
    private LocalDateTime fechaDeExpiracion;

    @Column(name = "id_colaboracion")
    private Long idColaboracion;

    @Enumerated(EnumType.STRING)
    private MotivoApertura motivoApertura;

    public static SolicitudApertura create(ColaboradorHumano colab, LocalDateTime fechaSoli, TarjetaColaborador tarjetaColaborador, Heladera heladera, Integer cantViandas, MotivoApertura motivo, Long idColaboracion){
        return SolicitudApertura
                .builder()
                .fechaHoraSolicitud(fechaSoli)
                .colaboradorHumano(colab)
                .solicitante(tarjetaColaborador)
                .idColaboracion(idColaboracion)
                .heladera(heladera)
                .cantidadDeViandas(cantViandas)
                .fechaDeExpiracion(fechaSoli.plusHours(horasParaEjecutarAccion))
                .motivoApertura(motivo)
                .build();

    }
    public boolean isDentroDeTiempo() {
        return LocalDateTime.now().isBefore(fechaDeExpiracion);
    }


    public Long getIdTarjeta() {
        return this.solicitante.getId();
    }

    public Long getIdHeladera() {
        return this.heladera.getId();
    }
}
