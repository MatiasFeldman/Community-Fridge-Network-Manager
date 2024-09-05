package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import ar.edu.utn.frba.dds.dtos.incidentes.IncidenteDTO;
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
public class Incidente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_incidente")
    private Long id;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Getter
    @ManyToOne
    @JoinColumn(name = "id_heladera", nullable = false)
    private Heladera heladera;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private TipoEvento tipo;

    @Column(name = "id_usuario", nullable = false)
    private Long idColaborador; // no seria mas practico tener al usuario/persona en vez del id

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
