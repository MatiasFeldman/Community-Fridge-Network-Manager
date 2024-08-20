package ar.edu.utn.frba.dds.models.entities.reportes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReporteMock implements Reporte {
    String nombre;
    String contenido;
    @Override
    public String nombre() {
        return nombre;
    }

    @Override
    public String contenido() {
        return contenido;
    }
}
