package ar.edu.utn.frba.dds.personas;

import lombok.Setter;

public class AtributoHumano {
    private String nombreAtributo;
    @Setter
    private String valorAtributo;

    public AtributoHumano(String nombreAtributo) {
        this.nombreAtributo = nombreAtributo;
    }
}
