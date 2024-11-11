package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "canjes")
public class Canjes extends Persistente {
    @ManyToOne
    @JoinColumn(name = "id_oferta")
    private Oferta oferta;

    @Column(name = "fecha_de_canje")
    private LocalDate fecha_de_canje;

    @Column(name = "puntos_necesario")
    private Double puntos_necesario;


}
