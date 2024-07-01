// RecomendarPuntos.java
package ar.edu.utn.frba.dds.models.entities.ubicacion;

import ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos.APIRecomendadoraDePuntos;
import ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos.molde.ListaDeUbicaciones;

import java.io.IOException;
import java.util.List;

public class RecomendarPuntos {
    private APIRecomendadoraDePuntos api;

    public RecomendarPuntos() {
        this.api = APIRecomendadoraDePuntos.getInstance();
    }

    public List<Coordenada> solicitarRecomendacionParaHeladera(Coordenada coordenada, double radio) throws IOException, InterruptedException {
        ListaDeUbicaciones obj = api.puntosIdeales(coordenada,radio);
        return obj.getCoordenadas();
    }


}
