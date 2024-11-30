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
            case "DONACION_VIANDAS" -> ContribucionHumanaFactory.crearDonacionDeViandaFinalizada(colaboradorHumano);
            case "REDISTRIBUCION_VIANDAS" -> ContribucionHumanaFactory.crearDistribucionDeViandaFinalizada(cant, colaboradorHumano);
            case "ENTREGA_TARJETAS" -> new RegistroPersonaVulnerable();
            default -> throw new InvalidContribucionException("Forma de contribucion invalida.");
        };
    }

    public static DonacionDeVianda crearDonacionDeVianda(ColaboradorHumano h, Heladera destino) {
        DonacionDeVianda donacion = DonacionDeVianda.of(destino, h , true);
        h.sumarPuntaje(donacion);
        return donacion;
    }

    public static DistribucionViandas crearDistribucionDeViandas(Heladera origen, Heladera destino, Integer cant, String motivo, ColaboradorHumano h) {
        destino.agregarViandas(cant);
        origen.quitarViandas(cant);
        DistribucionViandas distribucion = DistribucionViandas.of(origen, destino, cant, motivo, h);
        h.sumarPuntaje(distribucion);
        return distribucion;
    }

    public static DonacionDeDinero crearDonacionDeDineroPeriodica(double monto, ChronoUnit unidad, Integer frecuencia, ColaboradorHumano h) {
        DonacionDeDinero dinero =  DonacionDeDinero.of(h, monto, unidad, frecuencia);
        h.sumarPuntaje(dinero);
        return dinero;
    }

    public static DonacionDeDinero crearDonacionDeDineroPeriodica(double monto, ChronoUnit unidad, Integer frecuencia, Juridica j) {
        DonacionDeDinero dinero = DonacionDeDinero.of(j, monto, unidad, frecuencia);
        j.sumarPuntaje(dinero);
        return dinero;
    }

    public static DonacionDeDinero crearDonacionDeDinero(double monto, ColaboradorHumano colaboradorHumano) {
        DonacionDeDinero donacion = DonacionDeDinero.of(colaboradorHumano, monto);
        colaboradorHumano.sumarPuntaje(donacion);
        return donacion;
    }

    public static DonacionDeDinero crearDonacionDeDinero(double monto, Juridica j) {
        DonacionDeDinero donacion = DonacionDeDinero.of(j, monto);
        j.sumarPuntaje(donacion);
        return donacion;
    }

    public static RegistroPersonaVulnerable registrarPersonaVulnerable(TarjetaPersonaVulnerable tarjeta, PersonaVulnerable persona, Juridica j) {
        persona.setRegistradaPor(j);
        persona.setTarjetas(List.of(tarjeta));
        tarjeta.setDuenio(persona);
        RegistroPersonaVulnerable registro = RegistroPersonaVulnerable.of(tarjeta, j);
        j.sumarPuntaje(registro);
        return registro;
    }

    public static DonacionDeVianda crearDonacionDeViandaFinalizada(ColaboradorHumano colaboradorHumano) {
        DonacionDeVianda donacion = DonacionDeVianda.ofFinalizada(colaboradorHumano);
        colaboradorHumano.sumarPuntaje(donacion);
        return donacion;
    }

    public static DistribucionViandas crearDistribucionDeViandaFinalizada(Integer cant, ColaboradorHumano h) {
        DistribucionViandas distribucion = DistribucionViandas.ofCargaMasiva(cant, h);
        h.sumarPuntaje(distribucion);
        return distribucion;
    }
}
