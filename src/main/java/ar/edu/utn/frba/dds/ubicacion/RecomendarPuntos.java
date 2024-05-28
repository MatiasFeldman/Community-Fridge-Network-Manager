package ar.edu.utn.frba.dds.ubicacion;

import ar.edu.utn.frba.dds.exceptions.RecomendarPuntosException;
import ar.edu.utn.frba.dds.helpers.ConexionAPI;
import ar.edu.utn.frba.dds.ubicacion.Coordenada;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

public class RecomendarPuntos {

    public ArrayList<Coordenada> solicitarRecomendacionParaHeladera(Coordenada coordenada, Double radio) throws IOException, InterruptedException {
        String url = "https://b5d319cd-de7d-4fbc-9808-c101eab29c7d.mock.pstmn.io/api/ubicacion/lat=-34.58&lon=-58.43&radio=3";

        ConexionAPI api = new ConexionAPI(url);

        ArrayList<Coordenada> resultado = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        api.conectarse();
        String body = api.revisarRespuesta();

        if (body == null) {
            throw new RecomendarPuntosException("Error al recomendar los puntos para la heladera");
        }

        resultado = objectMapper.readValue(body, new TypeReference<ArrayList<Coordenada>>() {});
        return resultado;
    }
}
