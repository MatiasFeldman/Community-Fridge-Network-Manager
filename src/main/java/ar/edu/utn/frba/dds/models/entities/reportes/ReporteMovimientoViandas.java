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

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class ReporteMovimientoViandas implements Reporte {
    private HeladerasRepository heladeras;

    private LocalDate fechaUltimoReporte;

    private String contenido;

    public ReporteMovimientoViandas(HeladerasRepository heladeras) {
        this.heladeras = heladeras;
        this.fechaUltimoReporte = null;
        this.contenido = null;
    }


    public String generarReporteMovimientoViandas() {
        StringBuilder contenido = new StringBuilder();

        if (this.contenido != null && (this.fechaUltimoReporte != null && this.fechaUltimoReporte.isAfter(LocalDate.now().minusWeeks(1)))) {
            return contenido.toString();
        }

        contenido.append("Heladera Nombre\n");
        contenido.append("Entraron\n");
        contenido.append("Salieron\n");

        for (Heladera heladera : heladeras.buscarTodos()) {
            contenido.append(heladera.getNombre())
                    .append("\t\t")
                    .append(heladera.getViandasColocadas())
                    .append("\t\t")
                    .append(heladera.getViandasRetiradas())
                    .append("\n");
            heladera.setViandasColocadas(0);
            heladera.setViandasRetiradas(0);
        }
        this.fechaUltimoReporte = LocalDate.now();
        this.contenido = contenido.toString();
        return this.contenido;
    }


    @Override
    public String nombre() {
        return "Reporte de viandas por heladera";
    }

    @Override
    public String contenido() {
        return generarReporteMovimientoViandas();
    }

    @Override
    public int getNroColumnas() {
        return 3;
    }
}
