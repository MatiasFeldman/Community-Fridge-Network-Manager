package ar.edu.utn.frba.dds.models.entities.reportes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Reporte {
    private String nombre;
    private String contenido;
}
