package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.exceptions.InvalidContribucionException;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.entities.personas.PersonaVulnerable;

import java.time.temporal.ChronoUnit;

public class ContribucionHumanaFactory {
    public static Contribucion createForCargaMasiva(String strategy, Integer cant) {
        return switch (strategy) {
            case "DINERO" -> ContribucionHumanaFactory.crearDonacionDeDinero(cant);
            case "DONACION_VIANDA" -> ContribucionHumanaFactory.crearDonacionDeViandaFinalizada();
            case "REDISTRIBUCION_VIANDAS" -> ContribucionHumanaFactory.crearDistribucionDeViandaFinalizada(cant);
            case "ENTREGA_TARJETAS" -> new RegistroPersonaVulnerable();
            default -> throw new InvalidContribucionException("Forma de contribucion invalida.");
        };
    }

    public static DonacionDeVianda crearDonacionDeVianda(Heladera destino) {
        return DonacionDeVianda.of(destino);
    }

    public static DistribucionViandas crearDistribucionDeViandas(Heladera origen, Heladera destino, Integer cant, String motivo) {
        return DistribucionViandas.of(origen, destino, cant, motivo);
    }

    public static DonacionDeDinero crearDonacionDeDineroPeriodica(double monto, ChronoUnit unidad, Integer frecuencia) {
        return DonacionDeDinero.of(monto, unidad, frecuencia);
    }

    public static DonacionDeDinero crearDonacionDeDinero(double monto) {
        return DonacionDeDinero.of(monto);
    }

    public static RegistroPersonaVulnerable registrarPersonaVulnerable(TarjetaPersonaVulnerable tarjeta, PersonaVulnerable persona, Humano h) {
        persona.setRegistradaPor(h);
        persona.setTarjeta(tarjeta);
        tarjeta.setDuenio(persona);
        return RegistroPersonaVulnerable.of(tarjeta);
    }

    public static DonacionDeVianda crearDonacionDeViandaFinalizada() {
        return DonacionDeVianda.ofFinalizada();
    }

    public static DistribucionViandas crearDistribucionDeViandaFinalizada(Integer cant) {
        return DistribucionViandas.ofCargaMasiva(cant);
    }
}
