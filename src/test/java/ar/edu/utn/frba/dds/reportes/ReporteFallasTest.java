package ar.edu.utn.frba.dds.reportes;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Incidente;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.TipoEvento;
import ar.edu.utn.frba.dds.models.entities.reportes.ReporteFallas;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

class ReporteFallasTest {
    private IncidentesRepository incidentesRepository;
    private ReporteFallas reporteFallas;
    private List<Incidente> incidentes;

    @BeforeEach
    void setUp() {
        incidentesRepository = Mockito.mock(IncidentesRepository.class);
        reporteFallas = new ReporteFallas(incidentesRepository);

        // Inicializar lista simulada de incidentes
        incidentes = new ArrayList<>();

        // Configurar comportamiento del mock
        doAnswer(invocation -> {
            Incidente incidente = invocation.getArgument(0);
            incidentes.add(incidente);
            return null;
        }).when(incidentesRepository).guardar(any(Incidente.class));

        when(incidentesRepository.buscarTodos()).thenReturn(incidentes);
    }

    @Test
    void testNombre() {
        assertEquals("Reporte de fallas", reporteFallas.nombre());
    }

    @Test
    void testContenido() {
        // Crear heladeras
        Heladera heladera1 = Heladera.of("Heladera1");
        heladera1.setCapActual(10);  // Inicializar capacidad actual
        heladera1.setCapacidadMaxima(10);

        Heladera heladera2 = Heladera.of("Heladera2");
        heladera2.setCapActual(10);  // Inicializar capacidad actual
        heladera2.setCapacidadMaxima(10);

        // Crear incidentes usando el builder
        Incidente incidente1 = Incidente.builder()
                .fecha(LocalDateTime.now())
                .heladera(heladera1)
                .tipo(TipoEvento.FALLA_TECNICA)
                .build();
        incidentesRepository.guardar(incidente1);

        Incidente incidente2 = Incidente.builder()
                .fecha(LocalDateTime.now())
                .heladera(heladera2)
                .tipo(TipoEvento.FALLA_TECNICA)
                .build();
        incidentesRepository.guardar(incidente2);

        Incidente incidente3 = Incidente.builder()
                .fecha(LocalDateTime.now())
                .heladera(heladera1)
                .tipo(TipoEvento.FALLA_TECNICA)
                .build();
        incidentesRepository.guardar(incidente3);

        Incidente incidente4 = Incidente.builder()
                .fecha(LocalDateTime.now())
                .heladera(heladera1)
                .tipo(TipoEvento.MOVIMIENTO)
                .build();
        incidentesRepository.guardar(incidente4);

        // Generar el contenido del reporte
        String contenido = reporteFallas.contenido();
        String expected = "Reporte de fallas\nHeladera\tCantidad de fallas\nHeladera1\t3\nHeladera2\t1\n";

        // Imprimir valores para depuración
        System.out.println("Expected:\n" + expected);
        System.out.println("Actual:\n" + contenido);

        // Verificar el contenido del reporte
        assertEquals(expected, contenido);
    }
}