package ar.edu.utn.frba.dds.ubicacion;

import ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos.RecomendadoraDePuntosAPIService;
import ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos.molde.ListaDeUbicaciones;
import ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos.APIRecomendadoraDePuntos;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

public class RecomendadorDePuntosTest {
    @Mock
    private RecomendadoraDePuntosAPIService iRecomendadoraDePuntosAPI;

    @Mock
    private Call<ListaDeUbicaciones> mockCall;

    @Mock
    private Retrofit mockRetrofit;

    @InjectMocks
    private APIRecomendadoraDePuntos apiRecomendadoraDePuntos;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        when(mockRetrofit.create(RecomendadoraDePuntosAPIService.class)).thenReturn(iRecomendadoraDePuntosAPI);
        apiRecomendadoraDePuntos = new APIRecomendadoraDePuntos(mockRetrofit);
    }

    @Test
    public void testListaDeUbis() throws IOException {
        // Arrange
        ListaDeUbicaciones expectedListaDeUbicaciones = new ListaDeUbicaciones();
        expectedListaDeUbicaciones.coordenadas = Arrays.asList(
                new Coordenada(40.7128, -74.0060),
                new Coordenada(34.0522, -118.2437)
        );

        Response<ListaDeUbicaciones> response = Response.success(expectedListaDeUbicaciones);

        when(iRecomendadoraDePuntosAPI.recomendados(anyDouble(), anyDouble(), anyDouble())).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(response);

        // Act
        Coordenada coordenada = new Coordenada(40.7128, -74.0060);
        ListaDeUbicaciones actualListaDeUbicaciones = apiRecomendadoraDePuntos.puntosIdeales(coordenada, 10.0);

        // Assert
        assertEquals(expectedListaDeUbicaciones.coordenadas.size(), actualListaDeUbicaciones.coordenadas.size());
        assertEquals(expectedListaDeUbicaciones.coordenadas.get(0).getLatitud(), actualListaDeUbicaciones.coordenadas.get(0).getLatitud());
        assertEquals(expectedListaDeUbicaciones.coordenadas.get(0).getLongitud(), actualListaDeUbicaciones.coordenadas.get(0).getLongitud());
        assertEquals(expectedListaDeUbicaciones.coordenadas.get(1).getLatitud(), actualListaDeUbicaciones.coordenadas.get(1).getLatitud());
        assertEquals(expectedListaDeUbicaciones.coordenadas.get(1).getLongitud(), actualListaDeUbicaciones.coordenadas.get(1).getLongitud());
    }

    @Test
    public void testListaDeUbisIOException() throws IOException {
        // Arrange
        when(iRecomendadoraDePuntosAPI.recomendados(anyDouble(), anyDouble(), anyDouble())).thenReturn(mockCall);
        when(mockCall.execute()).thenThrow(new IOException());
        Coordenada coordenada = new Coordenada(40.7128, -74.0060);
        // Act & Assert
        assertThrows(IOException.class,()->{apiRecomendadoraDePuntos.puntosIdeales(coordenada, 10.0);});
    }
}