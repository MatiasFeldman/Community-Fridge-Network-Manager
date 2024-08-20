package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.exceptions.InvalidContribucionEsception;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;

import java.util.Map;
import java.util.UUID;

public class ContribucionHumanaFactory {
    public static ContribucionHumana createForCargaMasiva(String strategy, Integer cant) {
        return switch (strategy) {
            case "DINERO" -> new DonacionDeDinero(cant);
            case "DONACION_VIANDA" -> new DonacionDeVianda();
            case "REDISTRIBUCION_VIANDAS" -> new DistribucionViandas(cant);
            case "ENTREGA_TARJETAS" -> new RegistroPersonaVulnerable(cant);
            default -> throw new InvalidContribucionEsception("Forma de contribucion invalida.");
        };
    }

    public static DonacionDeVianda crearDonacionDeVianda(UUID idHumano, Heladera destino){
        return DonacionDeVianda.of(idHumano, destino);
    }

    public static DistribucionViandas crearDistribucionDeViandas(Heladera origen, Heladera destino, Integer cant, String motivo){
        return DistribucionViandas.of(origen, destino, cant, motivo);
    }
}
