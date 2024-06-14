package ar.edu.utn.frba.dds.ubicacion;

import ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos.molde.ListUbi;
import ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos.APIRecomendadoraDePuntos;
import ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos.IRecomendadoraDePuntosAPIService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

public class RecomendadorDePuntosTest {
    @Mock
    private IRecomendadoraDePuntosAPIService iRecomendadoraDePuntosAPI;

    @Mock
    private Call<ListUbi> mockCall;

    @Mock
    private Retrofit mockRetrofit;

    @InjectMocks
    private APIRecomendadoraDePuntos apiRecomendadoraDePuntos;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        when(mockRetrofit.create(IRecomendadoraDePuntosAPIService.class)).thenReturn(iRecomendadoraDePuntosAPI);
        apiRecomendadoraDePuntos = new APIRecomendadoraDePuntos(mockRetrofit);
    }

    @Test
    public void testListaDeUbis() throws IOException {
        // Arrange
        ListUbi expectedListUbi = new ListUbi();
        expectedListUbi.coordenadas = Arrays.asList(
                new Coordenada(40.7128, -74.0060),
                new Coordenada(34.0522, -118.2437)
        );

        Response<ListUbi> response = Response.success(expectedListUbi);

        when(iRecomendadoraDePuntosAPI.recomendados(anyDouble(), anyDouble(), anyDouble())).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(response);

        // Act
        ListUbi actualListUbi = apiRecomendadoraDePuntos.listaDeUbis(40.7128, -74.0060, 10.0);

        // Assert
        assertEquals(expectedListUbi.coordenadas.size(), actualListUbi.coordenadas.size());
        assertEquals(expectedListUbi.coordenadas.get(0).getLatitud(), actualListUbi.coordenadas.get(0).getLatitud());
        assertEquals(expectedListUbi.coordenadas.get(0).getLongitud(), actualListUbi.coordenadas.get(0).getLongitud());
        assertEquals(expectedListUbi.coordenadas.get(1).getLatitud(), actualListUbi.coordenadas.get(1).getLatitud());
        assertEquals(expectedListUbi.coordenadas.get(1).getLongitud(), actualListUbi.coordenadas.get(1).getLongitud());
    }

    @Test
    public void testListaDeUbisIOException() throws IOException {
        // Arrange
        when(iRecomendadoraDePuntosAPI.recomendados(anyDouble(), anyDouble(), anyDouble())).thenReturn(mockCall);
        when(mockCall.execute()).thenThrow(new IOException());

        // Act & Assert
        try {
            apiRecomendadoraDePuntos.listaDeUbis(40.7128, -74.0060, 10.0);
        } catch (IOException e) {
            assertEquals(IOException.class, e.getClass());
        }
    }
}
