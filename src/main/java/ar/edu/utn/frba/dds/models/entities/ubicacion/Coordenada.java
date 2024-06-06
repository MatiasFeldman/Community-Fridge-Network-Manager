package ar.edu.utn.frba.dds.models.entities.ubicacion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Coordenada {
    private double latitud;
    private double longitud;

    public Coordenada() {
    }
}
