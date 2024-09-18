package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.exceptions.InvalidContribucionException;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import ar.edu.utn.frba.dds.models.entities.personas.PersonaVulnerable;

import java.time.temporal.ChronoUnit;
import java.util.List;

public class ContribucionHumanaFactory {
    public static Contribucion createForCargaMasiva(String strategy, Integer cant, ColaboradorHumano colaboradorHumano) {
        return switch (strategy) {
            case "DINERO" -> ContribucionHumanaFactory.crearDonacionDeDinero(cant, colaboradorHumano);
            case "DONACION_VIANDA" -> ContribucionHumanaFactory.crearDonacionDeViandaFinalizada();
            case "REDISTRIBUCION_VIANDAS" -> ContribucionHumanaFactory.crearDistribucionDeViandaFinalizada(cant, colaboradorHumano);
            case "ENTREGA_TARJETAS" -> new RegistroPersonaVulnerable();
            default -> throw new InvalidContribucionException("Forma de contribucion invalida.");
        };
    }

    public static DonacionDeVianda crearDonacionDeVianda(ColaboradorHumano h, Heladera destino) {
        return DonacionDeVianda.of(destino, h, true);
    }

    public static DistribucionViandas crearDistribucionDeViandas(Heladera origen, Heladera destino, Integer cant, String motivo, ColaboradorHumano h) {
        return DistribucionViandas.of(origen, destino, cant, motivo, h);
    }

    public static DonacionDeDinero crearDonacionDeDineroPeriodica(double monto, ChronoUnit unidad, Integer frecuencia, ColaboradorHumano h) {
        return DonacionDeDinero.of(h, monto, unidad, frecuencia);
    }

    public static DonacionDeDinero crearDonacionDeDineroPeriodica(double monto, ChronoUnit unidad, Integer frecuencia, Juridica j) {
        return DonacionDeDinero.of(j, monto, unidad, frecuencia);
    }

    public static DonacionDeDinero crearDonacionDeDinero(double monto, ColaboradorHumano colaboradorHumano) {
        return DonacionDeDinero.of(colaboradorHumano, monto);
    }

    public static DonacionDeDinero crearDonacionDeDinero(double monto, Juridica j) {
        return DonacionDeDinero.of(j, monto);
    }

    public static RegistroPersonaVulnerable registrarPersonaVulnerable(TarjetaPersonaVulnerable tarjeta, PersonaVulnerable persona, ColaboradorHumano h) {
        persona.setRegistradaPor(h);
        persona.setTarjetas(List.of(tarjeta));
        tarjeta.setDuenio(persona);
        return RegistroPersonaVulnerable.of(tarjeta, h);
    }

    public static DonacionDeVianda crearDonacionDeViandaFinalizada() {
        return DonacionDeVianda.ofFinalizada();
    }

    public static DistribucionViandas crearDistribucionDeViandaFinalizada(Integer cant, ColaboradorHumano h) {
        return DistribucionViandas.ofCargaMasiva(cant, h);
    }
}
