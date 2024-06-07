package ar.edu.utn.frba.dds.ubicacion;

import ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos.IRecomendadoraDePuntosAPI;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.models.entities.ubicacion.RecomendarPuntos;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.mockito.Mockito.*;

public class RecomendadorDePuntosTest {


    @Test
    public void recomendarPuntosParaHeladeraTest() throws IOException, InterruptedException {
        IRecomendadoraDePuntosAPI apiImpostor = mock(IRecomendadoraDePuntosAPI.class);
        RecomendarPuntos recomendarPuntos = new RecomendarPuntos(apiImpostor);

        when(apiImpostor.revisarRespuesta()).thenReturn("[{\"latitud\":40.712776,\"longitud\":-74.005974},{\"latitud\":34.052235,\"longitud\":-118.243683}]");

        recomendarPuntos.solicitarRecomendacionParaHeladera(new Coordenada(1,2),0);

        verify(apiImpostor, times(1)).revisarRespuesta();
    }
}
