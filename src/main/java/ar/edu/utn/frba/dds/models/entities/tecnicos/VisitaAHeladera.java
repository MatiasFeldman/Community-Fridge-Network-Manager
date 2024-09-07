package ar.edu.utn.frba.dds.models.entities.tecnicos;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Incidente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.awt.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "servicio_a_heladera")
public class VisitaAHeladera {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_incidente", referencedColumnName = "id_incidente")
    private Incidente incidenteAResolver;

    @ManyToOne
    @JoinColumn(name = "id_tecnico", referencedColumnName = "id_tecnico")
    private Tecnico tecnico;

    @Column(name = "fecha_visita")
    private LocalDateTime fechaDeVisita;

    @Column(name = "solucionado")
    private Boolean solucionado;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "foto")
    private String foto = null;


    public static VisitaAHeladera crear(Incidente incidente, Tecnico tecnico, LocalDateTime fechaDeVisita, Boolean solucionado, String descripcion, String foto){
        return VisitaAHeladera
                .builder()
                .incidenteAResolver(incidente)
                .tecnico(tecnico)
                .fechaDeVisita(fechaDeVisita)
                .solucionado(solucionado)
                .foto(foto)
                .descripcion(descripcion)
                .build();
    }

    public static VisitaAHeladera crear(Incidente incidente, Tecnico tecnico, LocalDateTime fechaDeVisita, Boolean solucionado, String descripcion){
        return VisitaAHeladera
                .builder()
                .incidenteAResolver(incidente)
                .tecnico(tecnico)
                .fechaDeVisita(fechaDeVisita)
                .solucionado(solucionado)
                .descripcion(descripcion)
                .build();
    }


    public Heladera getHeladeraFallada() {
        return this.incidenteAResolver.getHeladera();
    }
}
