package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaHumano;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


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


    public UUID getIdHeladera(){
        return this.getIdHeladera();
    }

    public boolean isDentroDeTiempo() {
        return LocalDateTime.now().isBefore(fechaDeExpiracion);
    }


    public String getIdTarjeta() {
        return this.solicitante.getId();
    }
}
