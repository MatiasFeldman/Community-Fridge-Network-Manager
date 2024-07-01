package ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos;

import ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos.molde.ListaDeUbicaciones;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecomendadoraDePuntosAPIService {
    @GET("/api/ubicacion/")
    Call<ListaDeUbicaciones> recomendados(@Query("lat") double latitud, @Query("lon") double longitud, @Query("radio") double radio);

}
