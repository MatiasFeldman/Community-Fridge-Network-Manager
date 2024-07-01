package ar.edu.utn.frba.dds.models.entities.helpers.geocoding;

import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class OpenSourceGeocoding implements APIGeocode, APIDistancias{
    private static OpenSourceGeocoding instance = null;
    private static final String urlBase = "https://api.openrouteservice.org";
    protected Retrofit retrofit;

    public OpenSourceGeocoding() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    // Constructor para pruebas unitarias
    public OpenSourceGeocoding(Retrofit retrofit) {
        this.retrofit = retrofit;
    }


    public static OpenSourceGeocoding getInstance() {
        if (instance == null) {
            instance = new OpenSourceGeocoding();
        }
        return instance;
    }

    @Override
    public Coordenada direccionToCoordenada(Direccion direccion) throws IOException {
        GeocodingService geocodingAPI = this.retrofit.create(GeocodingService.class);
        Call<Coordenada> requestUbis = geocodingAPI.direccionToCoordenada("5b3ce3597851110001cf6248bc162878ff444b0086d3dba021ce7bd2", direccion.direccionCompleta());
        Response<Coordenada> responseUbis = requestUbis.execute();
        return responseUbis.body();
    }

    @Override
    public Double distanciaEntreCoordenadas(Coordenada coord1, Coordenada coord2) throws IOException {
        GeocodingService geocodingAPI = this.retrofit.create(GeocodingService.class);
        String value = String.valueOf(this.coordenadasToArray(coord1,coord2));
        Call<Double> requestDistancia = geocodingAPI.distanciaEntreCoordenadas("5b3ce3597851110001cf6248bc162878ff444b0086d3dba021ce7bd2",value,"metrics");
        Response<Double> response = requestDistancia.execute();
        return response.body();

    }

    public ArrayList<String> coordenadasToArray(Coordenada c1, Coordenada c2){
        String coord1 = String.valueOf(c1.getLongitud() + c1.getLatitud());
        String coord2 = String.valueOf(c2.getLongitud() + c2.getLatitud());
        ArrayList<String> resultado = new ArrayList<>();
        resultado.add(coord1);
        resultado.add(coord2);
        return resultado;
    }
}
