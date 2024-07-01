package ar.edu.utn.frba.dds.models.entities.reportes;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Incidente;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReporteFallas implements  IReporte {
    private IncidentesRepository incidentesRepository;

    public ReporteFallas(IncidentesRepository incidentesRepository) {
        this.incidentesRepository = incidentesRepository;
    }

    @Override
    public String nombre() {
        return "Reporte de fallas";
    }

    @Override
    public String contenido() {
        return generarReporteFallas();
    }

    public String generarReporteFallas() {
        Map<Heladera, Integer> heladeraConteo = contarHeladeras();
        StringBuilder contenido = new StringBuilder();
        contenido.append("Reporte de fallas\n");
        contenido.append("Heladera\tCantidad de fallas\n");
        for (Map.Entry<Heladera, Integer> entry : heladeraConteo.entrySet()) {
            contenido.append(entry.getKey().getNombre().getNombreDePunto()).append("\t").append(entry.getValue()).append("\n");
        }
        return contenido.toString();
    }

    public Map<Heladera, Integer> contarHeladeras() {
        List<Incidente> incidentes = incidentesRepository.buscarTodos();
        Map<Heladera, Integer> heladeraConteo = new HashMap<>();

        for (Incidente incidente : incidentes) {
            Heladera heladera = incidente.getHeladera();
            heladeraConteo.put(heladera, heladeraConteo.getOrDefault(heladera, 0) + 1);
        }

        return heladeraConteo;
    }
}
