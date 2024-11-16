package ar.edu.utn.frba.dds.dtos.visita_heladera;

import ar.edu.utn.frba.dds.models.entities.tecnicos.VisitaAHeladera;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.swing.text.DateFormatter;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class VisitaHeladeraOutputDTO {
    private String fecha;
    private Boolean resuelto;
    private String tecnico;
    private String heladera;
    private String descripcion;
    private String foto;
    private String tipo;

    public static VisitaHeladeraOutputDTO of(VisitaAHeladera visita){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String tipoInc = switch (visita.getIncidenteAResolver().getTipo()) {
            case FALLA_TECNICA -> "Falla Tecnica";
            case FALLA_CONEXION -> "Falla Conexion";
            case TEMPERATURA -> "Temperatura";
            case MOVIMIENTO -> "Movimiento";
        };
        return VisitaHeladeraOutputDTO.builder()
                .fecha(visita.getFechaDeVisita().format(formatter))
                .resuelto(visita.getSolucionado())
                .tecnico(visita.getTecnico().nombreCompleto())
                .heladera(visita.getIncidenteAResolver().getHeladera().getNombre())
                .descripcion(visita.getDescripcion())
                .foto(visita.getFoto())
                .tipo(tipoInc)
                .build();
    }
}
