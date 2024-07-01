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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReporteMovimientoViandasTest {
    private HumanosRepository humanosRepository;
    private PersonasVulnerablesRepository personasVulnerablesRepository;
    private ReporteMovimientoViandas reporteMovimientoViandas;
    private List<Humano> humanos;
    private List<PersonaVulnerable> personasVulnerables;

    @BeforeEach
    void setUp() {
        humanosRepository = Mockito.mock(HumanosRepository.class);
        personasVulnerablesRepository = Mockito.mock(PersonasVulnerablesRepository.class);
        reporteMovimientoViandas = new ReporteMovimientoViandas(humanosRepository, personasVulnerablesRepository);

        // Inicializar listas simuladas
        humanos = new ArrayList<>();
        personasVulnerables = new ArrayList<>();

        // Configurar comportamiento de los mocks
        doAnswer(invocation -> {
            Humano humano = invocation.getArgument(0);
            humanos.add(humano);
            return null;
        }).when(humanosRepository).guardar(any(Humano.class));

        doAnswer(invocation -> {
            PersonaVulnerable personaVulnerable = invocation.getArgument(0);
            personasVulnerables.add(personaVulnerable);
            return null;
        }).when(personasVulnerablesRepository).guardar(any(PersonaVulnerable.class));

        when(humanosRepository.buscarTodos()).thenReturn(humanos);
        when(personasVulnerablesRepository.buscarTodos()).thenReturn(personasVulnerables);
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
        heladera1.setCapacidadActual(10);  // Inicializar capacidad actual

        Heladera heladera2 = new Heladera();
        PuntoDeHeladera punto2 = new PuntoDeHeladera();
        punto2.setNombreDePunto("Heladera2");
        heladera2.setNombre(punto2);
        heladera2.setCapacidadActual(10);  // Inicializar capacidad actual

        // Crear distribuciones de viandas
        DistribucionViandas distribucion1 = new DistribucionViandas(heladera1, heladera2, 5, "Motivo1", LocalDate.now());
        DistribucionViandas distribucion2 = new DistribucionViandas(heladera2, heladera1, 3, "Motivo2", LocalDate.now());

        // Crear humano con contribuciones
        Humano humano1 = new Humano(null);
        humano1.setIdUsuario(UUID.randomUUID());
        humano1.agregarContribucion(distribucion1);
        humano1.agregarContribucion(distribucion2);
        humanosRepository.guardar(humano1);

        // Crear tarjeta
        Tarjeta tarjeta = new Tarjeta("12345");

        // Usar la tarjeta para añadir usos
        tarjeta.usarEn(heladera1); // Añadir un uso en heladera1
        tarjeta.usarEn(heladera2); // Añadir un uso en heladera2

        // Crear persona vulnerable y asignar tarjeta
        PersonaVulnerable personaVulnerable = new PersonaVulnerable("Persona1", LocalDate.now(), LocalDate.now(), null, "12345678", 2, humano1);
        personaVulnerable.setTarjeta(tarjeta);
        personasVulnerablesRepository.guardar(personaVulnerable);

        // Generar el contenido del reporte
        String contenido = reporteMovimientoViandas.contenido();

        // Crear contenido esperado y ordenarlo
        List<String> expectedLines = Arrays.asList(
                "Reporte de viandas por heladera",
                "Heladera Nombre\t\tEntraron\tSalieron",
                "Heladera1\t\t4\t\t5",
                "Heladera2\t\t6\t\t3"
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
