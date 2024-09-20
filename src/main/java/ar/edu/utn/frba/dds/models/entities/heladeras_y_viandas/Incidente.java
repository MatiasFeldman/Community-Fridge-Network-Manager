package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import ar.edu.utn.frba.dds.dtos.incidentes.IncidenteDTO;
import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "incidente")
public class Incidente extends Persistente {

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_heladera", nullable = false)
    private Heladera heladera;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private TipoEvento tipo;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_colaborador", referencedColumnName = "id_usuario",nullable = false)
    private Usuario colaborador;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "foto")
    private String foto;

    @Column(name = "resuelto", nullable = false)
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
