package ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos;

import ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos.molde.ListUbi;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class APIRecomendadoraDePuntos implements IRecomendadorDePuntos{
    private static APIRecomendadoraDePuntos instance = null;
    private static final String urlBase = "http://example.com";
    protected Retrofit retrofit;

    public APIRecomendadoraDePuntos() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    // Constructor para pruebas unitarias
    public APIRecomendadoraDePuntos(Retrofit retrofit) {
        this.retrofit = retrofit;
    }


    public static APIRecomendadoraDePuntos getInstance() {
        if (instance == null) {
            instance = new APIRecomendadoraDePuntos();
        }
        return instance;
    }

    @Override
    public ListUbi listaDeUbis(double lat,double lon,double radio) throws IOException {
        IRecomendadoraDePuntosAPIService iRecomendadoraDePuntosAPI = this.retrofit.create(IRecomendadoraDePuntosAPIService.class);
        Call<ListUbi> requestUbis = iRecomendadoraDePuntosAPI.recomendados(lat,lon,radio);
        Response<ListUbi> responseUbis = requestUbis.execute();
        return responseUbis.body();
    }

}






