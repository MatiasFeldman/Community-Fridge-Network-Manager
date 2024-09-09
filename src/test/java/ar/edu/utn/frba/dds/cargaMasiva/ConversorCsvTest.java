package ar.edu.utn.frba.dds.cargaMasiva;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.carga_masiva.CargaMasiva;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.carga_masiva.ConversorCSVReader;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail.MailSender;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.dao.HumanosCollection;
import ar.edu.utn.frba.dds.models.repositories.ofertas.OfertasRepository;
import ar.edu.utn.frba.dds.models.repositories.ofertas.dao.OfertasCollection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Optional;

public class ConversorCsvTest {
    private HumanosRepository humanos;
    private OfertasRepository ofertas;
    private MailSender mailSender;
    private ConversorCSVReader conversor;
    private String path;
    private CargaMasiva cargaMasiva;

    @BeforeEach
    public void setUp() {
        humanos = new HumanosRepository(new HumanosCollection(new ArrayList<>()));
        ofertas = Mockito.mock(OfertasRepository.class);
        mailSender = Mockito.mock(MailSender.class);
        conversor = new ConversorCSVReader(humanos, ofertas, mailSender);
        path = "/Users/juanc/Downloads/colaboradores.csv";
        cargaMasiva = new CargaMasiva(path, conversor);
    }

    @Test
    void testConversorCSV() {
        cargaMasiva.cargar();

        String dni1 = "12345678";
        Optional<Humano> h = humanos.buscarPorDocumento("DNI", dni1);

        String dni2 = "9101112";
        Optional<Humano> h2 = humanos.buscarPorDocumento("DNI", dni2);

        Double puntaje1Supuesto = 100.0;
        Double puntaje2Supuesto = 175.0;


        Assertions.assertEquals(h.get().calcularPuntaje(), puntaje1Supuesto, "El puntaje del humano 1 no es el esperado");
        System.out.println("Puntaje esperado de Juan Pérez: " + puntaje1Supuesto);
        System.out.println("Puntaje obtenido: " + h.get().calcularPuntaje());

        Assertions.assertEquals(h2.get().calcularPuntaje(), puntaje2Supuesto);
        System.out.println("Puntaje esperado de Luis Martinez: " + puntaje2Supuesto);
        System.out.println("Puntaje obtenido: " + h2.get().calcularPuntaje());

        Assertions.assertEquals(h.get().getDocumento("DNI"), "12345678");
        Assertions.assertEquals(h2.get().getDocumento("DNI"), "9101112");

    }
}
