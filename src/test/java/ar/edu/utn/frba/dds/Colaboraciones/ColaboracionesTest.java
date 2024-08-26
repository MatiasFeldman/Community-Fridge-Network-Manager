package ar.edu.utn.frba.dds.Colaboraciones;

import ar.edu.utn.frba.dds.exceptions.AccesoDenegadoHeladeraException;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.DonacionDeVianda;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaHumano;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.*;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ColaboracionesTest {


    private Heladera heladera;
    private Heladera heladeraDestino;
    private Humano humano;
    private TarjetaHumano tarjeta;
    //private Vianda viandaMock;

    @BeforeEach
    public void setUp() {
        heladera = Heladera.builder()
                .capacidadMaxima(100)
                .capActual(50)
                .fechaDePuestaEnFuncionamiento(LocalDate.now().minusMonths(2))
                .activa(true)
                .solicitudes(new ArrayList<>())  // Inicializar la lista de solicitudes
                .build();

        heladeraDestino = Heladera.builder()
                .capacidadMaxima(100)
                .capActual(30)
                .fechaDePuestaEnFuncionamiento(LocalDate.now().minusMonths(1))
                .activa(true)
                .solicitudes(new ArrayList<>())  // Inicializar la lista de solicitudes
                .build();

        humano = mock(Humano.class);
        tarjeta = new TarjetaHumano();
        tarjeta.setDuenio(humano);
        when(humano.getTarjeta()).thenReturn(tarjeta);
    }

    @Test
    public void testDonacionDeVianda() {
        // Preparar la donación
        DonacionDeVianda donacion = DonacionDeVianda.builder()
                .solicitante(tarjeta)
                .heladera(heladera)
                .finalizada(false)
                .build();

        // Ejecutar la contribución
        donacion.contribuir();

        // Verificar que la solicitud de apertura se agregó a la heladera
        assertEquals(1, heladera.getSolicitudes().size());

        assertEquals(tarjeta.getId(), heladera.getSolicitudes().get(0).getIdTarjeta());
    }

    @Test
    public void testDistribucionViandas() {
        // Preparar la distribución
        DistribucionViandas distribucion = new DistribucionViandas(10);
        distribucion.setHeladeraOrigen(heladera);
        distribucion.setHeladeraDestino(heladeraDestino);
        distribucion.setSolicitante(tarjeta);

        // Ejecutar la contribución
        distribucion.contribuir();

        // Verificar que las solicitudes de apertura se agregaron a ambas heladeras
        assertEquals(1, heladera.getSolicitudes().size());
        assertEquals(tarjeta.getId(), heladera.getSolicitudes().get(0).getIdTarjeta());

        assertEquals(1, heladeraDestino.getSolicitudes().size());
        assertEquals(tarjeta.getId(), heladeraDestino.getSolicitudes().get(0).getIdTarjeta());
    }

    @Test
    public void testAccesoDonacionPermitido() {
        // Preparar la donación
        DonacionDeVianda donacion = DonacionDeVianda.builder()
                .solicitante(tarjeta)
                .heladera(heladera)
                .finalizada(false)
                .build();

        // Ejecutar la contribución
        donacion.contribuir();

        // Verificar el acceso a la heladera con la tarjeta
        assertDoesNotThrow(() -> heladera.verificarAcceso(tarjeta.getId(), LocalDateTime.now()));

        // Verificar que el intento de apertura se registró
        assertEquals(1, heladera.getSolicitudes().size());
        assertTrue(heladera.getRegistrosAperturas().get(0).isAcceso());

        //verificamos que la vianda este entregada
        assertTrue(donacion.getFinalizada());
    }

    @Test
    public void testAccesoDonacionDenegado() {
        // Preparar una solicitud expirada
        SolicitudApertura solicitudExpirada = new SolicitudApertura(tarjeta, 1, AccionSobreViandas.RETIRAR, heladera);
        solicitudExpirada.setFechaHoraSolicitud(LocalDate.now().minusDays(1).atStartOfDay()); // Hace la solicitud expirada
        heladera.agregarSolicitudApertura(solicitudExpirada);

        // Verificar que el acceso a la heladera con la tarjeta es denegado
        assertThrows(AccesoDenegadoHeladeraException.class, () -> heladera.verificarAcceso(tarjeta.getId(), LocalDateTime.now()));

        // Verificar que el intento de apertura se registró
        assertEquals(1, heladera.getSolicitudes().size());
        assertFalse(heladera.getRegistrosAperturas().get(0).isAcceso());
    }

    @Test
    public void testAccesoDistribucionPermitido() {
        // Preparar la distribución
        DistribucionViandas distribucion = new DistribucionViandas(10);
        distribucion.setHeladeraOrigen(heladera);
        distribucion.setHeladeraDestino(heladeraDestino);
        distribucion.setSolicitante(tarjeta);

        // Ejecutar la contribución
        distribucion.contribuir();

        // Verificar el acceso a ambas heladeras con la tarjeta
        assertDoesNotThrow(() -> heladera.verificarAcceso(tarjeta.getId(), LocalDateTime.now()));
        assertDoesNotThrow(() -> heladeraDestino.verificarAcceso(tarjeta.getId(), LocalDateTime.now()));

        // Verificar que los intentos de apertura se registraron
        assertEquals(1, heladera.getSolicitudes().size());
        assertTrue(heladera.getRegistrosAperturas().get(0).isAcceso());

        assertEquals(1, heladeraDestino.getSolicitudes().size());
        assertTrue(heladeraDestino.getRegistrosAperturas().get(0).isAcceso());

        //verificar que se modificaron las cantidades de viandas en la heladera
        assertEquals(60, heladera.getCapActual());
        assertEquals(20, heladeraDestino.getCapActual());
    }

    @Test
    public void testAccesoDistribucionDenegado() {
        // Preparar una solicitud expirada
        SolicitudApertura solicitudExpirada = new SolicitudApertura(tarjeta, 1, AccionSobreViandas.INGRESAR, heladera);
        solicitudExpirada.setFechaHoraSolicitud(LocalDate.now().minusDays(1).atStartOfDay()); // Hace la solicitud expirada
        heladera.agregarSolicitudApertura(solicitudExpirada);

        // Verificar que el acceso a la heladera de origen con la tarjeta es denegado
        assertThrows(AccesoDenegadoHeladeraException.class, () -> heladera.verificarAcceso(tarjeta.getId(), LocalDateTime.now()));

        // Verificar que el intento de apertura se registró en la heladera de origen
        assertEquals(1, heladera.getSolicitudes().size());
        assertFalse(heladera.getRegistrosAperturas().get(0).isAcceso());

        // Preparar la solicitud para la heladera destino
        SolicitudApertura solicitudExpiradaDestino = new SolicitudApertura(tarjeta, 1, AccionSobreViandas.RETIRAR, heladeraDestino);
        solicitudExpiradaDestino.setFechaHoraSolicitud(LocalDate.now().minusDays(1).atStartOfDay()); // Hace la solicitud expirada
        heladeraDestino.agregarSolicitudApertura(solicitudExpiradaDestino);

        // Verificar que el acceso a la heladera de destino con la tarjeta es denegado
        assertThrows(AccesoDenegadoHeladeraException.class, () -> heladeraDestino.verificarAcceso(tarjeta.getId(), LocalDateTime.now()));

        // Verificar que el intento de apertura se registró en la heladera de destino
        assertEquals(1, heladeraDestino.getSolicitudes().size());
        assertFalse(heladeraDestino.getRegistrosAperturas().get(0).isAcceso());
    }
}

