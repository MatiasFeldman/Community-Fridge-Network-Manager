package ar.edu.utn.frba.dds.models.entities.helpers.geocoding;

import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;

import java.io.IOException;

public interface APIGeocode {
    public Coordenada direccionToCoordenada(Direccion direccion) throws IOException;
}
