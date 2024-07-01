package ar.edu.utn.frba.dds.reportes;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.Tarjeta;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.UsoTarjeta;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.PuntoDeHeladera;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.entities.personas.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.entities.reportes.ReporteMovimientoViandas;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.personasVulnerables.PersonasVulnerablesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

class ReporteMovimientoViandasTest {
    private HumanosRepository humanosRepository;
    private PersonasVulnerablesRepository personasVulnerablesRepository;
    private ReporteMovimientoViandas reporteMovimientoViandas;

    @BeforeEach
    void setUp() {
        humanosRepository = Mockito.mock(HumanosRepository.class);
        personasVulnerablesRepository = Mockito.mock(PersonasVulnerablesRepository.class);
        reporteMovimientoViandas = new ReporteMovimientoViandas(humanosRepository, personasVulnerablesRepository);
    }

    @Test
    void testNombre() {
        assertEquals("Reporte de viandas por heladera", reporteMovimientoViandas.nombre());
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

        // Crear distribuciones de viandas
        DistribucionViandas distribucion1 = new DistribucionViandas(heladera1, heladera2, 5, "falla" , LocalDate.now());
        DistribucionViandas distribucion2 = new DistribucionViandas(heladera2, heladera1, 3, "falla" , LocalDate.now());

        // Crear humano con contribuciones
        Humano humano1 = Mockito.mock(Humano.class);
        humanosRepository.guardar(humano1);
        humano1.colaborar(distribucion1);
        humano1.colaborar(distribucion2);

        // Crear lista de humanos
        List<Humano> humanos = Arrays.asList(humano1);

        // Crear tarjeta
        Tarjeta tarjeta = new Tarjeta("12345");

        // Usar la tarjeta para añadir usos
        tarjeta.usarEn(heladera1); // Añadir un uso en heladera1
        tarjeta.usarEn(heladera2); // Añadir un uso en heladera2

        // Crear persona vulnerable y asignar tarjeta
        PersonaVulnerable personaVulnerable = Mockito.mock(PersonaVulnerable.class);
        personasVulnerablesRepository.guardar(personaVulnerable);
        when(personaVulnerable.getTarjeta()).thenReturn(tarjeta);

        // Crear lista de personas vulnerables
        List<PersonaVulnerable> personasVulnerables = Arrays.asList(personaVulnerable);

        // Configurar mocks
        when(humanosRepository.buscarTodos()).thenReturn(humanos);
        when(personasVulnerablesRepository.buscarTodos()).thenReturn(personasVulnerables);

        // Generar el contenido del reporte
        String contenido = reporteMovimientoViandas.contenido();
        String expected = "Reporte de viandas por heladera\nHeladera Nombre\t\tEntraron\tSalieron\nHeladera1\t\t5\t\t1\nHeladera2\t\t3\t\t1\n";

        // Verificar el contenido del reporte
        assertEquals(expected, contenido);
    }
}
