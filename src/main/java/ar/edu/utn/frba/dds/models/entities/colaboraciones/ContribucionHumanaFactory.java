package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.exceptions.InvalidContribucionEsception;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.entities.personas.PersonaVulnerable;

import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.UUID;

public class ContribucionHumanaFactory {
    public static ContribucionHumana createForCargaMasiva(String strategy, Integer cant) {
        return switch (strategy) {
            case "DINERO" -> new DonacionDeDinero(cant);
            case "DONACION_VIANDA" -> new DonacionDeVianda();
            case "REDISTRIBUCION_VIANDAS" -> new DistribucionViandas(cant);
            case "ENTREGA_TARJETAS" -> new RegistroPersonaVulnerable();
            default -> throw new InvalidContribucionEsception("Forma de contribucion invalida.");
        };
    }

    public static DonacionDeVianda crearDonacionDeVianda(Heladera destino, TarjetaHumano tarjeta){
        return DonacionDeVianda.of(destino, tarjeta);
    }

    public static DistribucionViandas crearDistribucionDeViandas(Heladera origen, Heladera destino, Integer cant, String motivo, TarjetaHumano tarjeta){
        return DistribucionViandas.of(origen, destino, cant, motivo, tarjeta);
    }

    public static DonacionDeDinero crearDonacionDeDineroPeriodica(double monto, ChronoUnit unidad, Integer frecuencia){
        return DonacionDeDinero.of(monto, unidad, frecuencia);
    }

    public static DonacionDeDinero crearDonacionDeDinero(double monto){
        return DonacionDeDinero.of(monto);
    }

    public static RegistroPersonaVulnerable registrarPersonaVulnerable(TarjetaPersonaVulnerable tarjeta, PersonaVulnerable persona, Humano h){
        persona.setRegistradaPor(h);
        persona.setTarjeta(tarjeta);
        tarjeta.setDuenio(persona);
        return RegistroPersonaVulnerable.of(tarjeta);
    }
}
