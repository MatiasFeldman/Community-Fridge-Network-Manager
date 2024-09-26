package ar.edu.utn.frba.dds.dtos.heladeras;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class HeladeraOutputDTO {
    private String direccion;
    private Integer capacidadActual;
    private Integer capacidadMaxima;
    private Boolean activa;
}
