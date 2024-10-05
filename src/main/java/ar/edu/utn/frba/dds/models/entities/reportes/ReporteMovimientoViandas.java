package ar.edu.utn.frba.dds.models.entities.reportes;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.*;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.entities.personas.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.repositories.distribuciones_de_viandas.DistribucionesDeViandasRepository;
import ar.edu.utn.frba.dds.models.repositories.donaciones_de_vianda.DonacionesDeViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.personasVulnerables.PersonasVulnerablesRepository;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class ReporteMovimientoViandas implements Reporte {
    private HeladerasRepository heladeras;


    public String generarReporteMovimientoViandas() {
        StringBuilder contenido = new StringBuilder();
        contenido.append("Reporte de viandas por heladera\n");
        contenido.append("Heladera Nombre\t\tEntraron\tSalieron\n");

        for (Heladera heladera : heladeras.buscarTodos()){
            contenido.append(heladera.getNombre())
                    .append("\t\t")
                    .append(heladera.getViandasColocadas())
                    .append("\t\t")
                    .append(heladera.getViandasRetiradas())
                    .append("\n");
        }
        return contenido.toString();
    }


    @Override
    public String nombre() {
        return "Reporte de viandas por heladera";
    }

    @Override
    public String contenido() {
        return generarReporteMovimientoViandas();
    }
}
