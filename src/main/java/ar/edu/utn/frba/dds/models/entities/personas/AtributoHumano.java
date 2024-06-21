package ar.edu.utn.frba.dds.models.entities.personas;

import lombok.Getter;
import lombok.Setter;

@Getter
public class AtributoHumano {
    private String nombreAtributo;
    @Setter
    private String valorAtributo;

    public AtributoHumano(String nombreAtributo) {
        this.nombreAtributo = nombreAtributo;
    }

    public AtributoHumano(String nombreAtributo, String valorAtributo) {
        this.nombreAtributo = nombreAtributo;
        this.valorAtributo = valorAtributo;
    }

}
