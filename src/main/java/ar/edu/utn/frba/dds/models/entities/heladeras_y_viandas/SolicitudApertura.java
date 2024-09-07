package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaHumano;
import lombok.*;

import java.time.LocalDateTime;
import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "solicitud_apertura")
public class SolicitudApertura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud")
    private Long id;

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
