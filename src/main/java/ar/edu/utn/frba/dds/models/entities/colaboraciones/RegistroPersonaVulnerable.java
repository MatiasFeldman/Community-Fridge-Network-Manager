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
    private ArrayList<TarjetaPersonaVulnerable> tarjetaPersonaVulnerables;
    private Integer tarjetasRepartidas;
    // Se van creando a medida que se van entregando tarjetas
    // eL CONTROLADOR GENERA Y REGISTRA LA CONTRIBUCION
    // La accion del dominion es registrar la conribucion

    public RegistroPersonaVulnerable(ArrayList<TarjetaPersonaVulnerable> tarjetaPersonaVulnerables) {
        this.tarjetaPersonaVulnerables = new ArrayList<>(tarjetaPersonaVulnerables);
        this.tarjetasRepartidas = 0;
    }

    public RegistroPersonaVulnerable(Integer cantidadTarjetas) {
        this.tarjetaPersonaVulnerables = new ArrayList<>();
        this.tarjetasRepartidas = cantidadTarjetas;
    }

    @Override
    public void contribuir() {
        throw new UnsupportedOperationException("No se puede contribuir sin colaborador y persona vulnerable");
    }

    public void contribuir(Humano colaborador, PersonaVulnerable vulnerable) {
        TarjetaPersonaVulnerable tarjetaPersonaVulnerableARepartir = obtenerTarjetaSinDuenio();
        if (tarjetaPersonaVulnerableARepartir != null) {
            vulnerable.setTarjetaPersonaVulnerable(tarjetaPersonaVulnerableARepartir);
            tarjetaPersonaVulnerableARepartir.setDuenio(vulnerable);
            vulnerable.setRegistradaPor(colaborador);
            tarjetasRepartidas++;
            System.out.println("Se ha registrado a la persona vulnerable con la tarjeta. ");
        } else {
            throw new TarjetasAgotadasException("No hay mas tarjetas disponibles");
        }
    }

    @Override
    public double calcularPuntaje() {
        ConstantesMultiplicativas constantes = new ConstantesMultiplicativas();
        return constantes.getCteTarjetas() * tarjetasRepartidas;
    }



    public TarjetaPersonaVulnerable obtenerTarjetaSinDuenio() {
        for (TarjetaPersonaVulnerable tarjetaPersonaVulnerable : tarjetaPersonaVulnerables) {
            if (tarjetaPersonaVulnerable.getDuenio() == null) {
                return tarjetaPersonaVulnerable;
            }
        }
        return null; // Devuelve null si no hay tarjetas sin dueño
    }

    public Integer tarjetasDisponibles() {
        return tarjetaPersonaVulnerables.size() - tarjetasRepartidas;
    }


}
