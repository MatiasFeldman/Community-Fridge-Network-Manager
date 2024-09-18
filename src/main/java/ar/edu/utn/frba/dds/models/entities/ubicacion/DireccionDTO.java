package ar.edu.utn.frba.dds.models.entities.ubicacion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DireccionDTO {
    private Calle calle;
    private Integer altura;
    private Coordenada coordenada;
}
