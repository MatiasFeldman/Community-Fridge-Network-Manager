package ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos;

import ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos.molde.ListaDeUbicaciones;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class APIRecomendadoraDePuntos {
    private static APIRecomendadoraDePuntos instance = null;
    private static String urlBase = "http://example.com";
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
            instance = new APIRecomendadoraDePuntos("http://www.example.com");
        }
        return instance;
    }

    public ListaDeUbicaciones puntosIdeales(Coordenada coordenadas, double radio) throws IOException {
        /*
        RecomendadoraDePuntosAPIService iRecomendadoraDePuntosAPI = this.retrofit.create(RecomendadoraDePuntosAPIService.class);
        Call<ListaDeUbicaciones> requestUbis = iRecomendadoraDePuntosAPI.recomendados(coordenadas.getLatitud(),coordenadas.getLongitud(),radio);
        Response<ListaDeUbicaciones> responseUbis = requestUbis.execute();
         */
        List<Coordenada> todasCoordenadas = List.of(
                new Coordenada(-34.61634160945483, -58.429962561286445),
                new Coordenada(-34.61631687945472, -58.432101499999995),
                new Coordenada(-34.62403915581325, -58.46523206057033),
                new Coordenada(-34.61455152995398, -58.432745477595454),
                new Coordenada(-34.58842940934453, -58.391932174862596),
                new Coordenada(-34.584772081976844, -58.39812472988439)
        );

        List<Coordenada> seleccionadas = new ArrayList<>();
        Random random = new Random();

        while (seleccionadas.size() < 3) {
            int index = random.nextInt(todasCoordenadas.size());
            Coordenada seleccionada = todasCoordenadas.get(index);
            if (!seleccionadas.contains(seleccionada)) {
                seleccionadas.add(seleccionada);
            }
        }

        return new ListaDeUbicaciones(seleccionadas);
    }

}






