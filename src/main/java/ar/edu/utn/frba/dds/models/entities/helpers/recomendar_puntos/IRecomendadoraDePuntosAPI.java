package ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos;

import java.io.IOException;

public interface IRecomendadoraDePuntosAPI {
    public void get() throws IOException, InterruptedException;
    public String revisarRespuesta();

    void setLat(double latitud);

    void setLon(double longitud);

    void setRad(double radio);
}
