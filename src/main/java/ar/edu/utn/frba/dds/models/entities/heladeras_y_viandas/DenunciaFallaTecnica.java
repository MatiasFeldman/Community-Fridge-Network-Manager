package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import ar.edu.utn.frba.dds.dtos.incidentes.DenunciaFallaTecnicaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DenunciaFallaTecnica {
    private Object denunciante;
    private String descripcion = null;
    private Image foto = null;
    private LocalDateTime fecha;
    private Heladera heladera;

    public static DenunciaFallaTecnica of(DenunciaFallaTecnicaDTO dto){
        return DenunciaFallaTecnica
                .builder()
                .denunciante(dto.getDenunciante())
                .fecha(dto.getFecha())
                .descripcion(dto.getDescripcion())
                .foto(dto.getFoto())
                .heladera(dto.getHeladera())
                .build();
    }

}
