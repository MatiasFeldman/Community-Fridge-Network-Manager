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
    public void contribuir(){
        if(tarjetasDisponibles() <= 0){
            throw new RuntimeException("No hay tarjetas disponibles");
        } else {
            for (Tarjeta tarjeta : tarjetas) {
                if (tarjeta.getDuenio() == null) {
                    personaVulnerableActual.setTarjeta(tarjeta);
                    tarjeta.setDuenio(personaVulnerableActual);
                    tarjetasRepartidas++;
                    System.out.println("Se le asigno la tarjeta a la persona vulnerable");
                }
            }
        }
    }

    @Override
    public double asignarPuntaje() {
        return tarjetasRepartidas * ConstanteMultiplicativa.CONSTANTE_TARJETAS;
    }

    public Integer tarjetasDisponibles(){
        return tarjetas.size() - tarjetasRepartidas;
    }


}
