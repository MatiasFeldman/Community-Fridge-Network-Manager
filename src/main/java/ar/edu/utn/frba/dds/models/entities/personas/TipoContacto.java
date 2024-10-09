package ar.edu.utn.frba.dds.models.entities.personas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Getter
public class TipoContacto {
    @Column(name = "tipo_contacto")
    private String nombre;
}
