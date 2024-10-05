package ar.edu.utn.frba.dds.models.entities.ubicacion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GeoRefDeDirecc {
    private Comuna comuna;
    private Coordenada coords;
    private Provincia provincia;
}
