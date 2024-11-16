package ar.edu.utn.frba.dds.models.entities.tecnicos;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Incidente;
import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.awt.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "servicio_a_heladera")
public class VisitaAHeladera extends Persistente {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_incidente", referencedColumnName = "id_incidente")
    private Incidente incidenteAResolver;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
