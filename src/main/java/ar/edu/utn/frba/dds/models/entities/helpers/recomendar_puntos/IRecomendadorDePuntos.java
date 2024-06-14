package ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos;

import ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos.molde.ListUbi;

import java.io.IOException;

public interface IRecomendadorDePuntos {
    public ListUbi listaDeUbis(double lat, double lon, double radio) throws IOException;
}
