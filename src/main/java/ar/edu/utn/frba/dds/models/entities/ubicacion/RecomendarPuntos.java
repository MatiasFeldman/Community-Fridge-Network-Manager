// RecomendarPuntos.java
package ar.edu.utn.frba.dds.models.entities.ubicacion;

import ar.edu.utn.frba.dds.exceptions.RecomendarPuntosException;
import ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos.APIRecomendadoraDePuntos;
import ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos.IRecomendadoraDePuntosAPI;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

public class RecomendarPuntos {
    private APIRecomendadoraDePuntos api;

    public RecomendarPuntos() {
        this.api = APIRecomendadoraDePuntos.getInstance();
    }

    public ArrayList<Coordenada> solicitarRecomendacionParaHeladera(Coordenada coordenada, double radio) throws IOException, InterruptedException {
        api.setLat(coordenada.getLatitud());
        api.setLon(coordenada.getLongitud());
        api.setRad(radio);

        ArrayList<Coordenada> resultado;
        ObjectMapper objectMapper = new ObjectMapper();

        api.get();

        String body = api.revisarRespuesta();

        if (body == null || body.isEmpty()) {
            throw new RecomendarPuntosException("Error al recomendar los puntos para la heladera");
        }

        resultado = objectMapper.readValue(body, new TypeReference<ArrayList<Coordenada>>() {});
        return resultado;
    }
}
