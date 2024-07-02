package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MensajeAutorizacionHeladera {
    private Humano solicitante;
    private boolean resultado;

    public boolean getResultado() {
        return resultado;
    }
}
