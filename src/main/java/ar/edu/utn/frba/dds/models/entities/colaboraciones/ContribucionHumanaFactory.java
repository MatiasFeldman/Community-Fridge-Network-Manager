package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.exceptions.InvalidContribucionException;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import ar.edu.utn.frba.dds.models.entities.personas.PersonaVulnerable;

import java.time.temporal.ChronoUnit;

public class ContribucionHumanaFactory {
    public static Contribucion createForCargaMasiva(String strategy, Integer cant, Humano humano) {
        return switch (strategy) {
            case "DINERO" -> ContribucionHumanaFactory.crearDonacionDeDinero(cant, humano);
            case "DONACION_VIANDA" -> ContribucionHumanaFactory.crearDonacionDeViandaFinalizada();
            case "REDISTRIBUCION_VIANDAS" -> ContribucionHumanaFactory.crearDistribucionDeViandaFinalizada(cant, humano);
            case "ENTREGA_TARJETAS" -> new RegistroPersonaVulnerable();
            default -> throw new InvalidContribucionException("Forma de contribucion invalida.");
        };
    }

    public static DonacionDeVianda crearDonacionDeVianda(Humano h, Heladera destino) {
        return DonacionDeVianda.of(destino, h, true);
    }

    public static DistribucionViandas crearDistribucionDeViandas(Heladera origen, Heladera destino, Integer cant, String motivo, Humano h) {
        return DistribucionViandas.of(origen, destino, cant, motivo, h);
    }

    public static DonacionDeDinero crearDonacionDeDineroPeriodica(double monto, ChronoUnit unidad, Integer frecuencia, Humano h) {
        return DonacionDeDinero.of(h, monto, unidad, frecuencia);
    }

    public static DonacionDeDinero crearDonacionDeDineroPeriodica(double monto, ChronoUnit unidad, Integer frecuencia, Juridica j) {
        return DonacionDeDinero.of(j, monto, unidad, frecuencia);
    }

    public static DonacionDeDinero crearDonacionDeDinero(double monto, Humano humano) {
        return DonacionDeDinero.of(humano, monto);
    }

    public static DonacionDeDinero crearDonacionDeDinero(double monto, Juridica j) {
        return DonacionDeDinero.of(j, monto);
    }

    public static RegistroPersonaVulnerable registrarPersonaVulnerable(TarjetaPersonaVulnerable tarjeta, PersonaVulnerable persona, Humano h) {
        persona.setRegistradaPor(h);
        persona.setTarjeta(tarjeta);
        tarjeta.setDuenio(persona);
        return RegistroPersonaVulnerable.of(tarjeta, h);
    }

    public static DonacionDeVianda crearDonacionDeViandaFinalizada() {
        return DonacionDeVianda.ofFinalizada();
    }

    public static DistribucionViandas crearDistribucionDeViandaFinalizada(Integer cant, Humano h) {
        return DistribucionViandas.ofCargaMasiva(cant, h);
    }
}
