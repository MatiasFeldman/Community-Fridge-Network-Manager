package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaHumano;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolicitudApertura {

    private LocalDateTime fechaHoraSolicitud;
    private static Integer horasParaEjecutarAccion = 3;
    private TarjetaHumano solicitante;
    private Heladera heladera;
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
