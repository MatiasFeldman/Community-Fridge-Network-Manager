package ar.edu.utn.frba.dds.models.entities.ubicacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@AllArgsConstructor
@Embeddable
@NoArgsConstructor
@Getter
public class Provincia {
    @Column(name = "provincia")
    private String nombre;

}
