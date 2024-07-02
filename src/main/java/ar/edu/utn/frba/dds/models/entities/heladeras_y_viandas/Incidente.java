package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import ar.edu.utn.frba.dds.dtos.incidentes.IncidenteDTO;
import lombok.Builder;
import lombok.Getter;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Builder
public class Incidente {
    private LocalDateTime fecha;
    private Heladera heladera;
    private TipoEvento tipo;
    private Object colaborador;
    private String descripcion;
    private Image foto;
    private boolean resuelto;

    public static Incidente of(IncidenteDTO dto){
        return Incidente
                .builder()
                .fecha(dto.getFecha())
                .heladera(dto.getHeladera())
                .tipo(dto.getTipo())
                .colaborador(dto.getColaborador())
                .descripcion(dto.getDescripcion())
                .foto(dto.getFoto())
                .resuelto(false)
                .build();
    }

    public static Incidente fallaTecnica(DenunciaFallaTecnica denuncia){
        return Incidente
                .builder()
                .fecha(denuncia.getFecha())
                .heladera(denuncia.getHeladera())
                .tipo(TipoEvento.FALLA_TECNICA)
                .colaborador(denuncia.getDenunciante())
                .descripcion(denuncia.getDescripcion())
                .foto(denuncia.getFoto())
                .resuelto(false)
                .build();
    }


}
