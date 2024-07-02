package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DenunciaFallaTecnica {
    private String descripcion = null;
    private Image foto = null;
    private LocalDateTime fecha;
}
