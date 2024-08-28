package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import ar.edu.utn.frba.dds.dtos.incidentes.IncidenteDTO;
import lombok.Builder;
import lombok.Getter;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class Incidente {
    private LocalDateTime fecha;
    @Getter
    private Heladera heladera;
    private TipoEvento tipo;
    private UUID idColaborador;
    private String descripcion;
    private String foto;
    private boolean resuelto;

    public static Incidente of(IncidenteDTO dto){
        return Incidente
                .builder()
                .fecha(dto.getFecha())
                .heladera(dto.getHeladera())
                .tipo(dto.getTipo())
                .idColaborador(dto.getColaborador())
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
                .idColaborador(denuncia.getDenunciante())
                .descripcion(denuncia.getDescripcion())
                .foto(denuncia.getFoto())
                .resuelto(false)
                .build();
    }


}
