package ar.edu.utn.frba.dds.dtos.heladeras;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class HeladeraOutputDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private Integer capacidadActual;
    private Integer capacidadMaxima;
    private Boolean activa;
    private Double longitud;
    private Double latitud;

}
