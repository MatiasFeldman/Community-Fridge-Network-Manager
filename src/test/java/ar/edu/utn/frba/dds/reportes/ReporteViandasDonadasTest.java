package ar.edu.utn.frba.dds.reportes;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.ContribucionHumanaFactory;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.DonacionDeVianda;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.entities.reportes.ReporteViandasDonadas;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;



class ReporteViandasDonadasTest {
    private HumanosRepository humanosRepository;
    private ReporteViandasDonadas reporteViandasDonadas;
    private List<ColaboradorHumano> colaboradorHumanos;

    @BeforeEach
    void setUp() {
        humanosRepository = Mockito.mock(HumanosRepository.class);
        reporteViandasDonadas = new ReporteViandasDonadas(humanosRepository);

        // Inicializar lista simulada
        colaboradorHumanos = new ArrayList<>();

        // Configurar comportamiento de los mocks
        doAnswer(invocation -> {
            ColaboradorHumano colaboradorHumano = invocation.getArgument(0);
            colaboradorHumanos.add(colaboradorHumano);
            return null;
        }).when(humanosRepository).guardar(any(ColaboradorHumano.class));

        when(humanosRepository.buscarTodos()).thenReturn(colaboradorHumanos);
    }

    @Test
    void testNombre() {
        assertEquals("Reporte de viandas donadas", reporteViandasDonadas.nombre());
    }

    @Test
    void testContenido() throws IOException {
        // Crear humano con donaciones
        Usuario usuario1 = new Usuario("usuario1", "Pedritoclavounclavito12122343@", null);
        usuario1.setId(1L);
        ColaboradorHumano colaboradorHumano1 = ColaboradorHumano.crearVacio();
        colaboradorHumano1.setUser(usuario1);

        DonacionDeVianda donacion1 = ContribucionHumanaFactory.crearDonacionDeViandaFinalizada();
        DonacionDeVianda donacion2 = ContribucionHumanaFactory.crearDonacionDeViandaFinalizada();

        colaboradorHumano1.sumarPuntaje(donacion1);
        colaboradorHumano1.sumarPuntaje(donacion2);
        humanosRepository.guardar(colaboradorHumano1);

        Usuario usuario2 = new Usuario("usuario2", "Pedritoclavounclavito12122343#", null);
        usuario2.setId(2L);
        ColaboradorHumano colaboradorHumano2 = ColaboradorHumano.crearVacio();
        colaboradorHumano2.setUser(usuario2);

        DonacionDeVianda donacion3 = ContribucionHumanaFactory.crearDonacionDeViandaFinalizada();
        colaboradorHumano2.sumarPuntaje(donacion3);
        humanosRepository.guardar(colaboradorHumano2);

        // Generar el contenido del reporte
        String contenido = reporteViandasDonadas.contenido();

        // Crear contenido esperado y ordenarlo
        List<String> expectedLines = Arrays.asList(
                "Reporte de viandas donadas",
                "ColaboradorHumano\tCantidad de viandas",
                colaboradorHumano1.getIdUsuario() + "\t2",
                colaboradorHumano2.getIdUsuario() + "\t1"
        );
        String expected = expectedLines.stream().sorted().collect(Collectors.joining("\n"));

        // Ordenar el contenido real
        List<String> actualLines = Arrays.asList(contenido.split("\n"));
        String actual = actualLines.stream().sorted().collect(Collectors.joining("\n"));

        // Imprimir valores para depuración
        System.out.println("Expected:\n" + expected);
        System.out.println("Actual:\n" + actual);

        // Verificar el contenido del reporte
        assertEquals(expected, actual);
    }
}