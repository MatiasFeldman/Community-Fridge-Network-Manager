package ar.edu.utn.frba.dds.models.entities.comandos;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class Alerta {
    private String mensaje;
    private Heladera heladera;
    private LocalDateTime fecha;
}
