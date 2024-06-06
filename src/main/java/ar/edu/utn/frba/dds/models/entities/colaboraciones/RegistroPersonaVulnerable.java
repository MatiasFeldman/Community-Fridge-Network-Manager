package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.exceptions.TarjetasAgotadasException;
import ar.edu.utn.frba.dds.models.entities.personas.PersonaVulnerable;
import lombok.Setter;

import java.util.ArrayList;

public class RegistroPersonaVulnerable implements ContribucionHumana{
    private ArrayList<Tarjeta> tarjetas;
    @Setter
    private PersonaVulnerable personaVulnerableActual; // Lo pense como q se va a ir cambiando a medaida q registremos a una ?
    private Integer tarjetasRepartidas;

    public RegistroPersonaVulnerable(ArrayList<Tarjeta> tarjetas) {
        this.tarjetas = new ArrayList<>(tarjetas);
        this.tarjetasRepartidas = 0;
        this.personaVulnerableActual = null;
    }

    @Override
    public void contribuir() {
        Tarjeta tarjetaARepartir = obtenerTarjetaSinDuenio();
        if (tarjetaARepartir != null) {
            personaVulnerableActual.setTarjeta(tarjetaARepartir);
            tarjetaARepartir.setDuenio(personaVulnerableActual);
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

    public Tarjeta obtenerTarjetaSinDuenio() {
        for (Tarjeta tarjeta : tarjetas) {
            if (tarjeta.getDuenio() == null) {
                return tarjeta;
            }
        }
        return null; // Devuelve null si no hay tarjetas sin dueño
    }

    public Integer tarjetasDisponibles(){
        return tarjetas.size() - tarjetasRepartidas;
    }


}
