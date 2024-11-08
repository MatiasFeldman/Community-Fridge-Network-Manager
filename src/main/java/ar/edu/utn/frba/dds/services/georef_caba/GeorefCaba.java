package ar.edu.utn.frba.dds.services.georef_caba;

import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GeorefCaba implements GeorefGobAPI{

    private String url = "https://datosabiertos-usig-apis.buenosaires.gob.ar/geocoder/2.2/reversegeocoding?";

    @Override
    public String getDirecc(Coordenada cord) {
        String url_final = url + "x=" + cord.getLongitud() + "&y=" + cord.getLatitud();
        return this.hacerRequest(url_final);
    }

    @Override
    @SneakyThrows
    public String hacerRequest(String url) {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            String body = response.body();

            if (body.startsWith("(") && body.endsWith(")")) {
                body = body.substring(1, body.length() - 1);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(body);

            return jsonResponse.get("puerta").asText();
        } else {
            System.out.println("Error en la request: " + response.statusCode());
            return null;
        }
    }
}
