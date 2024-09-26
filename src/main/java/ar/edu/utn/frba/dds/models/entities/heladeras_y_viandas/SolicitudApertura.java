package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaColaborador;
import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
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
    @JoinColumn(name = "id_tarjeta", referencedColumnName = "id_tarjeta")
    private TarjetaColaborador solicitante;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_heladera", referencedColumnName = "id_heladera")
    private Heladera heladera;

    @Column(name = "cantidad_viandas", nullable = false)
    private Integer cantidadDeViandas;

    @Column(name = "fecha_expiracion", nullable = false)
    private LocalDateTime fechaDeExpiracion;

    public static SolicitudApertura create(LocalDateTime fechaSoli, TarjetaColaborador tarjetaColaborador, Heladera heladera, Integer cantViandas){
        return SolicitudApertura
                .builder()
                .fechaHoraSolicitud(fechaSoli)
                .solicitante(tarjetaColaborador)
                .heladera(heladera)
                .cantidadDeViandas(cantViandas)
                .fechaDeExpiracion(fechaSoli.plusHours(horasParaEjecutarAccion))
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
