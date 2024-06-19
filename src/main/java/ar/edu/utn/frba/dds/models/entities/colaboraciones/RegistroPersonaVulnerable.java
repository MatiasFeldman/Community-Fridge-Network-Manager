package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.exceptions.TarjetasAgotadasException;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.entities.personas.PersonaVulnerable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
public class RegistroPersonaVulnerable implements ContribucionHumana {
    private ArrayList<Tarjeta> tarjetas;
    private Integer tarjetasRepartidas;
    // Se van creando a medida que se van entregando tarjetas
    // eL CONTROLADOR GENERA Y REGISTRA LA CONTRIBUCION
    // La accion del dominion es registrar la conribucion

    public RegistroPersonaVulnerable(ArrayList<Tarjeta> tarjetas) {
        this.tarjetas = new ArrayList<>(tarjetas);
        this.tarjetasRepartidas = 0;
    }

    public RegistroPersonaVulnerable(Integer cantidadTarjetas) {
        this.tarjetas = new ArrayList<>();
        this.tarjetasRepartidas = cantidadTarjetas;
    }

    @Override
    public void contribuir() {
        throw new UnsupportedOperationException("No se puede contribuir sin colaborador y persona vulnerable");
    }

    public void contribuir(Humano colaborador, PersonaVulnerable vulnerable) {
        Tarjeta tarjetaARepartir = obtenerTarjetaSinDuenio();
        if (tarjetaARepartir != null) {
            vulnerable.setTarjeta(tarjetaARepartir);
            tarjetaARepartir.setDuenio(vulnerable);
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



    public Tarjeta obtenerTarjetaSinDuenio() {
        for (Tarjeta tarjeta : tarjetas) {
            if (tarjeta.getDuenio() == null) {
                return tarjeta;
            }
        }
        return null; // Devuelve null si no hay tarjetas sin dueño
    }

    public Integer tarjetasDisponibles() {
        return tarjetas.size() - tarjetasRepartidas;
    }


}
