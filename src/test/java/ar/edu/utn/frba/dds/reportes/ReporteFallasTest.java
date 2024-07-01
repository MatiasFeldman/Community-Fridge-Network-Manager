package ar.edu.utn.frba.dds.reportes;

import ar.edu.utn.frba.dds.dtos.incidentes.IncidenteDTO;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Incidente;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.PuntoDeHeladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.TipoEvento;
import ar.edu.utn.frba.dds.models.entities.reportes.ReporteFallas;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ReporteFallasTest {
    private IncidentesRepository incidentesRepository;
    private ReporteFallas reporteFallas;

    @BeforeEach
    void setUp() {
        incidentesRepository = Mockito.mock(IncidentesRepository.class);
        reporteFallas = new ReporteFallas(incidentesRepository);
    }

    @Test
    void testNombre() {
        assertEquals("Reporte de fallas", reporteFallas.nombre());
    }

    @Test
    void testContenido() {
        // Crear heladeras
        Heladera heladera1 = new Heladera();
        PuntoDeHeladera punto1 = new PuntoDeHeladera();
        punto1.setNombreDePunto("Heladera1");
        heladera1.setNombre(punto1);

        Heladera heladera2 = new Heladera();
        PuntoDeHeladera punto2 = new PuntoDeHeladera();
        punto2.setNombreDePunto("Heladera2");
        heladera2.setNombre(punto2);

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

        List<Incidente> incidentes = Arrays.asList(incidente1, incidente2, incidente3);

        // Configurar el mock para que retorne los incidentes creados
        when(incidentesRepository.buscarTodos()).thenReturn(incidentes);

        // Generar el contenido del reporte
        String contenido = reporteFallas.contenido();
        String expected = "Reporte de fallas\nHeladera\tCantidad de fallas\nHeladera1\t2\nHeladera2\t1\n";

        // Verificar el contenido del reporte
        assertEquals(expected, contenido);
    }
}
