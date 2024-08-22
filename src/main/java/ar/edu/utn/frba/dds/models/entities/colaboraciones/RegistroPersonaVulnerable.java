package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.exceptions.TarjetasAgotadasException;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.entities.personas.PersonaVulnerable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
public class RegistroPersonaVulnerable implements ContribucionHumana {
    private TarjetaPersonaVulnerable tarjetaRepartida;

    public static RegistroPersonaVulnerable of(TarjetaPersonaVulnerable tarjetaRepartida) {
        return new RegistroPersonaVulnerable(tarjetaRepartida);
    }

    @Override
    public double calcularPuntaje() {
        ConstantesMultiplicativas constantes = new ConstantesMultiplicativas();
        return constantes.getCteTarjetas();
    }


}
