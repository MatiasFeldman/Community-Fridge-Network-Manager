package ar.edu.utn.frba.dds.reportes;

import ar.edu.utn.frba.dds.main.MainReportes;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.ContribucionHumanaFactory;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.DonacionDeVianda;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Incidente;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.TipoEvento;
import ar.edu.utn.frba.dds.models.entities.helpers.reportes.GeneradorPDF;
import ar.edu.utn.frba.dds.models.entities.helpers.reportes.PDFgenerator;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.entities.personas.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.entities.reportes.GenerarReportesCronJob;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.repositories.distribuciones_de_viandas.DistribucionesDeViandasRepository;
import ar.edu.utn.frba.dds.models.repositories.donaciones_de_vianda.DonacionesDeViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TestVisual {
    @BeforeEach
    public void setup() {
        IncidentesRepository incidentesRepository = ServiceLocator.instanceOf(IncidentesRepository.class);
        HeladerasRepository heladerasRepository = ServiceLocator.instanceOf(HeladerasRepository.class);
        HumanosRepository humanosRepository = ServiceLocator.instanceOf(HumanosRepository.class);
        DonacionesDeViandaRepository donacionesDeViandaRepository = ServiceLocator.instanceOf(DonacionesDeViandaRepository.class);
        DistribucionesDeViandasRepository distribucionesDeViandasRepository = ServiceLocator.instanceOf(DistribucionesDeViandasRepository.class);

        cargarReporteFalllas(heladerasRepository, incidentesRepository);
        cargarReporteViandasDoanadas(humanosRepository, donacionesDeViandaRepository);
        cargarReporteMovimientos(heladerasRepository, distribucionesDeViandasRepository, humanosRepository, donacionesDeViandaRepository);
    }

    @Test
    public void testGeneracionDeReporte() {
        String filePath = "C:\\Nico\\utn\\3°Año\\Diseño\\TPA\\probando_reportes";
        GeneradorPDF generadorPDF = new PDFgenerator();
        GenerarReportesCronJob reportesCronJob = new GenerarReportesCronJob(generadorPDF, filePath);

        MainReportes main = new MainReportes(reportesCronJob);
        main.ejecutarUnaVez();
    }


    // carga para el test de fallas
    public void cargarReporteFalllas(HeladerasRepository heladerasRepository, IncidentesRepository incidentesRepository) {

        Heladera heladera1 = Heladera.of("Heladera1");
        heladera1.setCapActual(10);
        heladera1.setCapacidadMaxima(10);

        Heladera heladera2 = Heladera.of("Heladera2");
        heladera2.setCapActual(10);
        heladera2.setCapacidadMaxima(10);

        heladerasRepository.guardar(heladera1);
        heladerasRepository.guardar(heladera2);

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
    }

    // cargar reporte viandas donadas
    public void cargarReporteViandasDoanadas(HumanosRepository humanosRepository, DonacionesDeViandaRepository donacionesDeViandaRepository) {
        Usuario usuario1 = new Usuario("usuario1", "prueba", null);
        usuario1.setId(1L);
        ColaboradorHumano colaboradorHumano1 = ColaboradorHumano.crearVacio();
        colaboradorHumano1.setUser(usuario1);

        Usuario usuario2 = new Usuario("usuario2", "prueba", null);
        usuario2.setId(2L);
        ColaboradorHumano colaboradorHumano2 = ColaboradorHumano.crearVacio();
        colaboradorHumano2.setUser(usuario2);


        humanosRepository.guardar(colaboradorHumano1);
        humanosRepository.guardar(colaboradorHumano2);

        DonacionDeVianda donacion1 = ContribucionHumanaFactory.crearDonacionDeViandaFinalizada(colaboradorHumano1);
        DonacionDeVianda donacion2 = ContribucionHumanaFactory.crearDonacionDeViandaFinalizada(colaboradorHumano1);
        DonacionDeVianda donacion3 = ContribucionHumanaFactory.crearDonacionDeViandaFinalizada(colaboradorHumano2);

        colaboradorHumano1.sumarPuntaje(donacion1);
        colaboradorHumano1.sumarPuntaje(donacion2);
        colaboradorHumano2.sumarPuntaje(donacion3);

        donacionesDeViandaRepository.guardar(donacion1);
        donacionesDeViandaRepository.guardar(donacion2);
        donacionesDeViandaRepository.guardar(donacion3);
    }


    public void cargarReporteMovimientos(HeladerasRepository heladerasRepository, DistribucionesDeViandasRepository distribucionesDeViandasRepository, HumanosRepository humanosRepository, DonacionesDeViandaRepository donacionesDeViandaRepository) {
        Heladera heladera3 = Heladera.of("Heladera3");
        heladera3.setCapActual(10);  // Inicializar capacidad actual
        heladera3.setCapacidadMaxima(10);


        Heladera heladera4 = Heladera.of("Heladera4");
        heladera4.setCapActual(10);  // Inicializar capacidad actual
        heladera4.setCapacidadMaxima(10);

        heladerasRepository.guardar(heladera3);
        heladerasRepository.guardar(heladera4);

        Usuario usuario = new Usuario("usuario3", "Pedritoclavounclavito123@", null);
        usuario.setId(3L);
        ColaboradorHumano colaboradorHumano3 = ColaboradorHumano.crearVacio();
        colaboradorHumano3.setUser(usuario);


        DistribucionViandas distribucion1 = ContribucionHumanaFactory.crearDistribucionDeViandas(heladera3, heladera4, 5, "Motivo1", colaboradorHumano3);
        DistribucionViandas distribucion2 = ContribucionHumanaFactory.crearDistribucionDeViandas(heladera4, heladera3, 3, "Motivo2", colaboradorHumano3);

        distribucionesDeViandasRepository.guardar(distribucion1);
        distribucionesDeViandasRepository.guardar(distribucion2);

        colaboradorHumano3.sumarPuntaje(distribucion1);
        colaboradorHumano3.sumarPuntaje(distribucion2);

        humanosRepository.guardar(colaboradorHumano3);

        TarjetaPersonaVulnerable tarjetaVulnerable = new TarjetaPersonaVulnerable();
        tarjetaVulnerable.setId(2L);

        // Usar la tarjeta para añadir usos
        tarjetaVulnerable.usarEn(heladera3); // Añadir un uso en heladera1
        tarjetaVulnerable.usarEn(heladera4); // Añadir un uso en heladera2

        // Crear donación de viandas
        DonacionDeVianda donacion1 = DonacionDeVianda.of(heladera3, colaboradorHumano3);
        DonacionDeVianda donacion2 = DonacionDeVianda.of(heladera4, colaboradorHumano3);
        DonacionDeVianda donacion3 = DonacionDeVianda.of(heladera3, colaboradorHumano3);
        DonacionDeVianda donacion4 = DonacionDeVianda.of(heladera4, colaboradorHumano3);

        donacionesDeViandaRepository.guardar(donacion1);
        donacionesDeViandaRepository.guardar(donacion2);
        donacionesDeViandaRepository.guardar(donacion3);
        donacionesDeViandaRepository.guardar(donacion4);

        colaboradorHumano3.sumarPuntaje(donacion1);
        colaboradorHumano3.sumarPuntaje(donacion2);
        colaboradorHumano3.sumarPuntaje(donacion3);
        colaboradorHumano3.sumarPuntaje(donacion4);

        // entraron 5 en la heladera3 y 7 en la heladera4
        // salieron 6 de la heladera3 y 4 de la heladera4
    }
}
