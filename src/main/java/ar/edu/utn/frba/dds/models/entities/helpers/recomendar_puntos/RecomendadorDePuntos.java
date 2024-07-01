package ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos;

import ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos.molde.ListaDeUbicaciones;

import java.io.IOException;

public interface RecomendadorDePuntos {
    public ListaDeUbicaciones listaDeUbis(double lat, double lon, double radio) throws IOException;
}
