package ar.edu.utn.frba.dds.models.entities.ubicacion;

import ar.edu.utn.frba.dds.exceptions.RecomendarPuntosException;
import ar.edu.utn.frba.dds.models.entities.helpers.ConexionAPI;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

public class RecomendarPuntos {
    private ConexionAPI api;
    public RecomendarPuntos(ConexionAPI api){
        this.api = api;
    }

    public ArrayList<Coordenada> solicitarRecomendacionParaHeladera(Coordenada coordenada, Double radio) throws IOException, InterruptedException {
        String url = "https://b5d319cd-de7d-4fbc-9808-c101eab29c7d.mock.pstmn.io";
        String params = "/api/ubicacion/lat=" + coordenada.getLatitud() + "&lon=" + coordenada.getLongitud() + "&radio=" + radio;

        api.setUrl(url);
        api.setParams(params);

        ArrayList<Coordenada> resultado;
        ObjectMapper objectMapper = new ObjectMapper();

        api.get();
        String body = api.revisarRespuesta();

        if (body == null) {
            throw new RecomendarPuntosException("Error al recomendar los puntos para la heladera");
        }

        resultado = objectMapper.readValue(body, new TypeReference<ArrayList<Coordenada>>() {});
        return resultado;
    }
}
