package ar.edu.utn.frba.dds.services.georef;

import ar.edu.utn.frba.dds.models.entities.ubicacion.Comuna;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.models.entities.ubicacion.GeoRefDeDirecc;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Provincia;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class GobiernoAPI implements IGeoRefApi {
    private String baseUrl = "https://apis.datos.gob.ar/georef/api/";

    @Override
    @SneakyThrows
    public GeoRefDeDirecc getCoordYComuna(String direccion, String provincia) {
        String provinciaEncoded = URLEncoder.encode(provincia, StandardCharsets.UTF_8);
        String direccionEncoded = URLEncoder.encode(direccion, StandardCharsets.UTF_8);
        String url = baseUrl + "direcciones?direccion=" + direccionEncoded + "&provincia=" + provinciaEncoded;

        return this.hacerRequest(url);
    }

    @Override
    @SneakyThrows
    public GeoRefDeDirecc hacerRequest(String url) {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            String body = response.body();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(body);

            JsonNode direcciones = jsonResponse.get("direcciones");

            if (!direcciones.isEmpty()) {
                JsonNode direc = direcciones.get(0);

                JsonNode ubicacion = direc.get("ubicacion");
                Double lat = ubicacion.get("lat").asDouble();
                Double lon = ubicacion.get("lon").asDouble();

                JsonNode departamento = direc.get("departamento");
                String comuna = departamento.get("nombre").asText();

                JsonNode provincia = direc.get("provincia");
                String prov = provincia.get("nombre").asText();

                return new GeoRefDeDirecc(new Comuna(comuna), new Coordenada(lat, lon), new Provincia(prov));
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
