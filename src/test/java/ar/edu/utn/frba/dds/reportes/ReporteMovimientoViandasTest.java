package ar.edu.utn.frba.dds.reportes;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.*;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import ar.edu.utn.frba.dds.models.entities.personas.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.entities.reportes.ReporteMovimientoViandas;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.repositories.distribuciones_de_viandas.DistribucionesDeViandasRepository;
import ar.edu.utn.frba.dds.models.repositories.donaciones_de_vianda.DonacionesDeViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.personasVulnerables.PersonasVulnerablesRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReporteMovimientoViandasTest {
    private HumanosRepository humanosRepository;
    private DonacionesDeViandaRepository donacionesDeViandaRepository;
    private DistribucionesDeViandasRepository distribucionesDeViandasRepository;
    private PersonasVulnerablesRepository personasVulnerablesRepository;
    private ReporteMovimientoViandas reporteMovimientoViandas;
    private HeladerasRepository heladeras;

    private List<ColaboradorHumano> colaboradorHumanos;
    private List<PersonaVulnerable> personasVulnerables;

    @BeforeEach
    void setUp() {
        humanosRepository = ServiceLocator.instanceOf(HumanosRepository.class);
        personasVulnerablesRepository = ServiceLocator.instanceOf(PersonasVulnerablesRepository.class);
        donacionesDeViandaRepository = ServiceLocator.instanceOf(DonacionesDeViandaRepository.class);
        distribucionesDeViandasRepository = ServiceLocator.instanceOf(DistribucionesDeViandasRepository.class);
        heladeras = ServiceLocator.instanceOf(HeladerasRepository.class);
        reporteMovimientoViandas = new ReporteMovimientoViandas(heladeras);
    }

    @Test
    void testNombre() {
        assertEquals("Reporte de viandas por heladera", reporteMovimientoViandas.nombre());
    }

    @Test
    void testContenido() throws IOException {
        // Crear heladeras
        Heladera heladera1 = Heladera.of("Heladera1");
        heladera1.setCapActual(10);  // Inicializar capacidad actual
        heladera1.setCapacidadMaxima(10);


        Heladera heladera2 = Heladera.of("Heladera2");
        heladera2.setCapActual(10);  // Inicializar capacidad actual
        heladera2.setCapacidadMaxima(10);

        heladeras.guardar(heladera1);
        heladeras.guardar(heladera2);

        // Crear distribuciones de viandas


        // Crear humano con contribuciones
        Usuario usuario = new Usuario("usuario1", "Pedritoclavounclavito123@", null);
        ColaboradorHumano colaboradorHumano1 = ColaboradorHumano.crearVacio();
        colaboradorHumano1.setUser(usuario);


        DistribucionViandas distribucion1 = ContribucionHumanaFactory.crearDistribucionDeViandas(heladera1, heladera2, 5, "Motivo1", colaboradorHumano1);
        DistribucionViandas distribucion2 = ContribucionHumanaFactory.crearDistribucionDeViandas(heladera2, heladera1, 3, "Motivo2", colaboradorHumano1);

        distribucionesDeViandasRepository.guardar(distribucion1);
        distribucionesDeViandasRepository.guardar(distribucion2);

        colaboradorHumano1.sumarPuntaje(distribucion1);
        colaboradorHumano1.sumarPuntaje(distribucion2);

        humanosRepository.guardar(colaboradorHumano1);

        // Crear tarjeta
        TarjetaPersonaVulnerable tarjetaVulnerable = new TarjetaPersonaVulnerable();
        tarjetaVulnerable.setId(2L);

        // Usar la tarjeta para añadir usos
        tarjetaVulnerable.usarEn(heladera1); // Añadir un uso en heladera1
        tarjetaVulnerable.usarEn(heladera2); // Añadir un uso en heladera2

        // Crear donación de viandas
        DonacionDeVianda donacion1 = DonacionDeVianda.of(heladera1, colaboradorHumano1);
        DonacionDeVianda donacion2 = DonacionDeVianda.of(heladera2, colaboradorHumano1);

        donacionesDeViandaRepository.guardar(donacion1);
        donacionesDeViandaRepository.guardar(donacion2);

        colaboradorHumano1.sumarPuntaje(donacion1);
        colaboradorHumano1.sumarPuntaje(donacion2);

        Juridica juridica = new Juridica();

        // Crear persona vulnerable y asignar tarjeta
        PersonaVulnerable personaVulnerable = new PersonaVulnerable("Persona1", LocalDate.now(), LocalDate.now(), null, "12345678", 2, juridica);
        personaVulnerable.setTarjetas(List.of(tarjetaVulnerable));
        personasVulnerablesRepository.guardar(personaVulnerable);

        // Generar el contenido del reporte
        String contenido = reporteMovimientoViandas.contenido();

        // Crear contenido esperado y ordenarlo
        List<String> expectedLines = Arrays.asList(
                "Reporte de viandas por heladera",
                "Heladera Nombre\t\tEntraron\tSalieron",
                "Heladera1\t\t4\t\t6",
                "Heladera2\t\t6\t\t4"
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
