package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaHumano;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "solicitud_de_apertura")
public class SolicitudApertura {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "fecha_hora_solicitud", nullable = false)
    private LocalDateTime fechaHoraSolicitud;

    private static Integer horasParaEjecutarAccion = 3;

    @ManyToOne
    @JoinColumn(name = "id_tarjeta", referencedColumnName = "id_tarjeta")
    private TarjetaHumano solicitante;

    @ManyToOne
    @JoinColumn(name = "id_heladera", referencedColumnName = "id_heladera")
    private Heladera heladera;

    @Column(name = "cantidad_viandas", nullable = false)
    private Integer cantidadDeViandas;
    private LocalDateTime fechaDeExpiracion;

    public static SolicitudApertura create(LocalDateTime fechaSoli, TarjetaHumano tarjetaHumano, Heladera heladera, Integer cantViandas){
        return SolicitudApertura
                .builder()
                .fechaHoraSolicitud(fechaSoli)
                .solicitante(tarjetaHumano)
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
