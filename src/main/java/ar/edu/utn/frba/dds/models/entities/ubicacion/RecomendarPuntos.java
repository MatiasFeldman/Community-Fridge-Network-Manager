// RecomendarPuntos.java
package ar.edu.utn.frba.dds.models.entities.ubicacion;

import ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos.APIRecomendadoraDePuntos;
import ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos.molde.ListUbi;

import java.io.IOException;
import java.util.List;

public class RecomendarPuntos {
    private APIRecomendadoraDePuntos api;

    public RecomendarPuntos() {
        this.api = APIRecomendadoraDePuntos.getInstance();
    }

    public List<Coordenada> solicitarRecomendacionParaHeladera(Coordenada coordenada, double radio) throws IOException, InterruptedException {
        ListUbi obj = api.listaDeUbis(coordenada.getLatitud(),coordenada.getLongitud(),radio);


        return obj.getCoordenadas();
    }


}
