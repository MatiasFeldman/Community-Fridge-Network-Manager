package ar.edu.utn.frba.dds.models.entities.personas;

import lombok.Getter;
import lombok.Setter;

public class AtributoHumano {
    @Getter
    private String nombreAtributo;
    @Setter
    @Getter
    private String valorAtributo;

    public AtributoHumano(String nombreAtributo) {
        this.nombreAtributo = nombreAtributo;
    }
}
