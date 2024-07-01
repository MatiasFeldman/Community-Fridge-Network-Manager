package ar.edu.utn.frba.dds.models.entities.helpers.geocoding;

import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;

import java.io.IOException;

public interface APIDistancias {
    public Double distanciaEntreCoordenadas(Coordenada coord1, Coordenada coord2) throws IOException;
}
