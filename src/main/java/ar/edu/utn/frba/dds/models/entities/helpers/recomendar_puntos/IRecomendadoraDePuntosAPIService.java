package ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos;

import ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos.molde.ListUbi;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IRecomendadoraDePuntosAPIService {
//    public void get() throws IOException, InterruptedException;
//    public String revisarRespuesta();
//
//    void setLat(double latitud);
//
//    void setLon(double longitud);
//
//    void setRad(double radio);

    //Con retrofit
    @GET("/api/ubicacion/")
    Call<ListUbi> recomendados(@Query("lat") double latitud, @Query("lon") double longitud, @Query("radio") double radio);


}
