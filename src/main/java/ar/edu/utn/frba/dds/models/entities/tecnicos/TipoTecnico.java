package ar.edu.utn.frba.dds.models.entities.tecnicos;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TipoTecnico {
    @Column(name = "tipo")
    private String tipo;
}
