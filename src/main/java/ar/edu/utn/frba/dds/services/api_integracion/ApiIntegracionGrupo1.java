package ar.edu.utn.frba.dds.services.api_integracion;

import ar.edu.utn.frba.dds.exceptions.APIIntegracionSinConexionException;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.models.entities.ubicacion.GeoRefDeDirecc;
import ar.edu.utn.frba.dds.models.entities.ubicacion.LugarDonacion;
import ar.edu.utn.frba.dds.services.georef.GobiernoAPI;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ApiIntegracionGrupo1 implements APIAdapter {
    @Override
    @SneakyThrows
    public List<LugarDonacion> getLugaresCercanos(Coordenada coordenadas) {
        // Hace el request a la API de donaciones cercanas
        String urlBase = "http://localhost:8000";
        String url = urlBase + "/recomendacion/?latitud=" + coordenadas.getLatitud() + "&longitud=" + coordenadas.getLongitud();
        HttpClient client = HttpClient.newHttpClient();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            // Si la respuesta es 200, se parsea la respuesta y se obtienen los hospitales cercanos
            if (response.statusCode() == 200) {
                String body = response.body();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonResponse = objectMapper.readTree(body);
                List<LugarDonacion> lugares = new ArrayList<>();
                for (JsonNode direcciones : jsonResponse) {
                    String nombre = direcciones.get("nombre").asText();
                    String direccion = direcciones.get("direccion").asText();
                    System.out.println("Nombre: " + nombre + " Direccion: " + direccion);
                    GobiernoAPI api = new GobiernoAPI();
                    GeoRefDeDirecc geoRefDeDirecc = api.getCoord(direccion);

                    if (geoRefDeDirecc != null) {
                        System.out.println(geoRefDeDirecc.getCoords().latitud + " " + geoRefDeDirecc.getCoords().longitud);
                        lugares.add(new LugarDonacion(nombre, geoRefDeDirecc.getCoords(), direccion));
                    } else {
                        System.out.println("No se pudo obtener la direccion de: " + nombre);
                    }
                }

                return lugares;
            }else throw new APIIntegracionSinConexionException();
        } catch (java.net.ConnectException e) {
            throw new APIIntegracionSinConexionException();
        }
    }
}
