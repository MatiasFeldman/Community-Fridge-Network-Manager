package ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos;

import ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos.molde.ListaDeUbicaciones;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class APIRecomendadoraDePuntos {
    private static APIRecomendadoraDePuntos instance = null;
    private static String urlBase; //recibirlo por constructor
    protected Retrofit retrofit;

    public APIRecomendadoraDePuntos(String urlBase) {
        this.urlBase = urlBase;
        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    // Constructor para pruebas unitarias
    public APIRecomendadoraDePuntos(Retrofit retrofit) {
        this.retrofit = retrofit;
    }
    //Constructor


    public static APIRecomendadoraDePuntos getInstance() {
        if (instance == null) {
            instance = new APIRecomendadoraDePuntos("www.example.com");
        }
        return instance;
    }

    public ListaDeUbicaciones puntosIdeales(Coordenada coordenadas, double radio) throws IOException {
        RecomendadoraDePuntosAPIService iRecomendadoraDePuntosAPI = this.retrofit.create(RecomendadoraDePuntosAPIService.class);
        Call<ListaDeUbicaciones> requestUbis = iRecomendadoraDePuntosAPI.recomendados(coordenadas.getLatitud(),coordenadas.getLongitud(),radio);
        Response<ListaDeUbicaciones> responseUbis = requestUbis.execute();
        return responseUbis.body();
    }

}






