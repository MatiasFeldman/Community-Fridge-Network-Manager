package ar.edu.utn.frba.dds.models.entities.reportes;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Incidente;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
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

    @Override
    public int getNroColumnas(){return 2;}

    public String generarReporteFallas() {
        StringBuilder contenido = new StringBuilder();
        contenido.append("Heladera\n");
        contenido.append("Cantidad de fallas\n");
        for (Heladera heladera : heladeras.buscarTodos()) {
            contenido.append(heladera.getNombre()).append("\t").append(incidentesRepository.cantFallasTecnicasEn(heladera)).append("\n");
        }
        return contenido.toString();
    }

}
