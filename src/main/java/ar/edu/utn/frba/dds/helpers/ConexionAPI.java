package ar.edu.utn.frba.dds.helpers;

import ar.edu.utn.frba.dds.exceptions.ConexionAPIException;
import lombok.Setter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConexionAPI {
    private String url;
    @Setter
    private HttpResponse<String> response;
    @Setter
    private Integer statusCode;
    @Setter
    private String body;

    public ConexionAPI(String url) {
        this.url = url;
        response = null;
        statusCode = null;
        body = null;
    }

    public void conectarse() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        setResponse(HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()));
        setStatusCode(response.statusCode());
        setBody(response.body());
    }

    public String revisarRespuesta(){
        if(statusCode == 200){
            return body;
        }
        else {
            throw new ConexionAPIException("Peticion fallida; Status Code: " + statusCode);
        }
    }
}
