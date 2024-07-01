package ar.edu.utn.frba.dds.models.entities.helpers.geocoding;

import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GeocodingService {
    @GET("/geocode/search/structured")
    Call<Coordenada> direccionToCoordenada(@Query("api_key") String key, @Query("address") String direccion);

    @POST("/v2/matrix/driving-car")
    Call<Double> distanciaEntreCoordenadas(@Query("api_key") String key, @Query("locations") String coordenadas, @Query("metrics") String metrica);
}
