package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class Rubro {
    @Column(name = "rubro")
    private String nombre;
}
