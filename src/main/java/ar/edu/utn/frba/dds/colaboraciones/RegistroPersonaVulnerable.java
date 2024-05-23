package ar.edu.utn.frba.dds.colaboraciones;

import ar.edu.utn.frba.dds.helpers.ConstanteMultiplicativa;
import ar.edu.utn.frba.dds.personas.PersonaVulnerable;
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
    public void contribuir(ColaboracionesRealizadas colaboracionesRealizadas) {
        Tarjeta tarjetaARepartir = obtenerTarjetaSinDuenio();
        if (tarjetaARepartir != null) {
            personaVulnerableActual.setTarjeta(tarjetaARepartir);
            tarjetaARepartir.setDuenio(personaVulnerableActual);
            tarjetasRepartidas++;
            colaboracionesRealizadas.aumentarTarjetasRepartidas();
            System.out.println("Se ha registrado a la persona vulnerable con la tarjeta. ");
        } else {
            System.out.println("No hay tarjetas disponibles para registrar a la persona vulnerable.");
        }
    }

    public Tarjeta obtenerTarjetaSinDuenio() {
        for (int i = 0; i < tarjetas.size(); i++) {
            Tarjeta tarjeta = tarjetas.get(i);
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
