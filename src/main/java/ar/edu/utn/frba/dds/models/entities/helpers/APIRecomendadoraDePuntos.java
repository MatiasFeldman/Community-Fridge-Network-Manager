package ar.edu.utn.frba.dds.models.entities.helpers;

import ar.edu.utn.frba.dds.exceptions.ConexionAPIException;
import lombok.Setter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class APIRecomendadoraDePuntos implements IRecomendadoraDePuntosAPI {
    private String url;
    private String params;
    @Setter
    private double lat;
    @Setter
    private double lon;
    @Setter
    private double rad;
    @Setter
    private HttpResponse<String> response;
    @Setter
    private Integer statusCode;
    @Setter
    private String body;

    public APIRecomendadoraDePuntos() {
        this.url = "https://b5d319cd-de7d-4fbc-9808-c101eab29c7d.mock.pstmn.io";
        this.params = "/api/ubicacion/lat=" + this.lat + "&lon=" + this.lon + "&radio=" + this.rad;
        response = null;
        statusCode = null;
        body = null;
        lat = 0;
        lon = 0;
        rad = -1;
    }

    @Override
    public void get() throws IOException, InterruptedException {
        String urlFinal = url + params;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlFinal))
                .GET()
                .build();

        setResponse(HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()));
        setStatusCode(response.statusCode());
        setBody(response.body());
    }

    @Override
    public String revisarRespuesta(){
        if(statusCode == 200){
            return body;
        }
        else {
            throw new ConexionAPIException("Peticion fallida; Status Code: " + statusCode);
        }
    }
}
