package ar.edu.utn.frba.dds.models.entities.reportes;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Incidente;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@AllArgsConstructor
public class ReporteFallas implements Reporte {
    private IncidentesRepository incidentesRepository;
    private HeladerasRepository heladeras;

    @Override
    public String nombre() {
        return "Reporte de fallas";
    }

    @Override
    public String contenido() {
        return generarReporteFallas();
    }

    public String generarReporteFallas() {
        StringBuilder contenido = new StringBuilder();
        contenido.append("Reporte de fallas\n");
        contenido.append("Heladera\tCantidad de fallas\n");
        for (Heladera heladera : heladeras.buscarTodos()) {
            contenido.append(heladera.getNombre()).append("\t").append(incidentesRepository.cantFallasTecnicasEn(heladera)).append("\n");
        }
        return contenido.toString();
    }

}
