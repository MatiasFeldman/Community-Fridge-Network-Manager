package ar.edu.utn.frba.dds.models.entities.tecnicos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class TipoTecnico {
    @Column(name = "tipo")
    private String tipo;
}
