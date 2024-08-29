package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

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
    private String idTarjeta;
    private UUID idHeladera;
    private Integer cantidadDeViandas;
    private LocalDateTime fechaDeExpiracion;


    public static SolicitudApertura create(LocalDateTime fechaSoli, String idTarjetaSolicitante, UUID idHeladera, Integer cantViandas){
        return SolicitudApertura
                .builder()
                .fechaHoraSolicitud(fechaSoli)
                .idTarjeta(idTarjetaSolicitante)
                .idHeladera(idHeladera)
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


}
