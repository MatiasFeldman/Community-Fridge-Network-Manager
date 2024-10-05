package ar.edu.utn.frba.dds.models.entities.ubicacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class Comuna {

    @Column(name = "comuna")
    private String nombre;
}
