package ar.edu.utn.frba.dds.dtos.incidentes;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import lombok.Getter;

import java.awt.*;
import java.time.LocalDateTime;

@Getter
public class DenunciaFallaTecnicaDTO {
    private Object denunciante;
    private String descripcion = null;
    private Image foto = null;
    private LocalDateTime fecha;
    private Heladera heladera;
}
