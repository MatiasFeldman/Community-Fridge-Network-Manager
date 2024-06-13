package ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos;

import ar.edu.utn.frba.dds.exceptions.ConexionAPIException;
import lombok.Setter;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class APIRecomendadoraDePuntos {
    private static APIRecomendadoraDePuntos instance = null;
    private static final String urlBase = "https://b5d319cd-de7d-4fbc-9808-c101eab29c7d.mock.pstmn.io";

    private IRecomendadoraDePuntosAPI api;
    private Retrofit retrofit;
    @Setter
    private double lat;
    @Setter
    private double lon;
    @Setter
    private double rad;
    @Setter
    private Response<String> response;
    @Setter
    private Integer statusCode;
    @Setter
    private String body;

    public APIRecomendadoraDePuntos() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.api = retrofit.create(IRecomendadoraDePuntosAPI.class);
    }

    public static APIRecomendadoraDePuntos getInstance() {
        if (instance == null) {
            instance = new APIRecomendadoraDePuntos();
        }
        return instance;
    }

    public void get() throws IOException {
        Call<String> call = api.getRecomendaciones(lat, lon, rad);
        response = call.execute();

        setStatusCode(response.code());
        setBody(response.body());
    }

    public String revisarRespuesta() {
        if (statusCode == 200) {
            return body;
        } else {
            throw new ConexionAPIException("Peticion fallida; Status Code: " + statusCode);
        }
    }
}






