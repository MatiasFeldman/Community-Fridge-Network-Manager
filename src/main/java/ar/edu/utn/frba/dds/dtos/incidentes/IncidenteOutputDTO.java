package ar.edu.utn.frba.dds.dtos.incidentes;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Incidente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class IncidenteOutputDTO {
    private String fechaReporte;
    private String fechaResuelto;
    private String descripcion;
    private String tipo;
    private String foto;
    private Boolean resuelto;
    private String reportadoPor;

    public static IncidenteOutputDTO of(Incidente i){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        IncidenteOutputDTOBuilder builder = IncidenteOutputDTO.builder();
        builder.fechaReporte((i.getFecha()).format(formatter))
                .descripcion(i.getDescripcion())
                .foto(i.getFoto())
                .reportadoPor(i.getColaborador().getUser())
                .resuelto(i.getResuelto());
        if (i.getResuelto()){
            builder.fechaResuelto((i.getFechaResuelto()).format(formatter));
        } else{
            builder.fechaResuelto(null);
        }

        switch (i.getTipo()){
            case FALLA_TECNICA -> builder.tipo("Falla técnica");
            case FALLA_CONEXION -> builder.tipo("Falla de conexión");
            case TEMPERATURA -> builder.tipo("Temperatura");
            case MOVIMIENTO -> builder.tipo("Movimiento");
        }
        return builder.build();
    }
}
