package ar.edu.utn.frba.dds.models.entities.helpers;

import ar.edu.utn.frba.dds.exceptions.ConexionAPIException;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@NoArgsConstructor
public class ConexionAPI {
    @Setter
    private String url;
    @Setter
    private String params;
    @Setter
    private HttpResponse<String> response;
    @Setter
    private Integer statusCode;
    @Setter
    private String body;

    public ConexionAPI(String url, String params) {
        this.url = url;
        this.params = params;
        response = null;
        statusCode = null;
        body = null;
    }




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

    public String revisarRespuesta(){
        if(statusCode == 200){
            return body;
        }
        else {
            throw new ConexionAPIException("Peticion fallida; Status Code: " + statusCode);
        }
    }
}
