package ar.edu.utn.frba.dds.adapters;

import ar.edu.utn.frba.dds.ubicacion.Coordenada;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class APIAdapter {

    public ArrayList<Coordenada> solicitarRecomendacionParaHeladera(Coordenada coordenada, Double radio) throws IOException, InterruptedException {

        String url = "https://3793ef15-66a5-4683-8154-2e750a9b296b.mock.pstmn.io/api/ubicacion/lat=-34.58&lon=-58.43&radio=3";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        ArrayList<Coordenada> resultado = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        if (response.statusCode() == 200) {
            String body = response.body();
            resultado = objectMapper.readValue(body, new TypeReference<ArrayList<Coordenada>>() {
            });
            for (Coordenada obtenida : resultado){
                System.out.println("Coordenada obtenida: " + obtenida.getLatitud() + ", " + obtenida.getLongitud());
            }
            return resultado;
        } else {
            System.out.println("Error al obtener la recomendacion de heladeras: " + response.statusCode());
            return new ArrayList<>();
        }
    }
}
