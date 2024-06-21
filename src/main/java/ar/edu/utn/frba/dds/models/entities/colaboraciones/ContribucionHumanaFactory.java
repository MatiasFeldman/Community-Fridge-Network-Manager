package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.exceptions.InvalidContribucionEsception;

public class ContribucionHumanaFactory {
    public static ContribucionHumana create(String strategy, Integer cant) {
        return switch (strategy) {
            case "DINERO" -> new DonacionDeDinero(cant);
            case "DONACION_VIANDAS" -> new DonacionDeVianda();
            case "REDISTRIBUCION_VIANDAS" -> new DistribucionViandas(cant);
            case "ENTREGA_TARJETAS" -> new RegistroPersonaVulnerable(cant);
            default -> throw new InvalidContribucionEsception("Forma de contribucion invalida.");
        };
    }
}
