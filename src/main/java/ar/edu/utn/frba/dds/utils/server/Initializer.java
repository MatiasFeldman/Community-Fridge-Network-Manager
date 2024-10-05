package ar.edu.utn.frba.dds.utils.server;

import ar.edu.utn.frba.dds.dtos.direccion.DireccionInputDTO;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.personas.Atributo;
import ar.edu.utn.frba.dds.models.entities.personas.TipoAtributo;
import ar.edu.utn.frba.dds.models.entities.personas.TipoCampoAtributo;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.models.factories.DireccionFactory;
import ar.edu.utn.frba.dds.models.repositories.atributos_humano.AtributosHumanoRepository;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;

public class Initializer {
    public static void init() {
        HeladerasRepository heladeras = ServiceLocator.instanceOf(HeladerasRepository.class);

        Direccion d1 = DireccionFactory.create(new DireccionInputDTO("Mozart 2300", "CABA"));

        Direccion d2 = DireccionFactory.create(new DireccionInputDTO("Pepiri 1234", "CABA"));

        Direccion d3 = DireccionFactory.create(new DireccionInputDTO("Avenida Triunvirato 4000", "CABA"));

        Direccion d4 = DireccionFactory.create(new DireccionInputDTO("Nazca 2000", "CABA"));

        Heladera h1 = Heladera
                .builder()
                .nombre("Heladera UTN Lugano")
                .direccion(d1)
                .capacidadMaxima(50)
                .capActual(50)
                .activa(true)
                .build();

        Heladera h2 = Heladera
                .builder()
                .nombre("Heladera 2")
                .direccion(d2)
                .capacidadMaxima(30)
                .capActual(30)
                .activa(true)
                .build();

        Heladera h3 = Heladera
                .builder()
                .nombre("Heladera 3")
                .direccion(d3)
                .capacidadMaxima(20)
                .capActual(20)
                .activa(true)
                .build();

        Heladera h4 = Heladera
                .builder()
                .nombre("Heladera Flores")
                .direccion(d4)
                .capacidadMaxima(70)
                .capActual(70)
                .activa(true)
                .build();

        heladeras.guardar(h1);
        heladeras.guardar(h2);
        heladeras.guardar(h3);
        heladeras.guardar(h4);

        Initializer.inicializarAtributos();
    }

    public static void inicializarAtributos(){
        AtributosHumanoRepository atributos = ServiceLocator.instanceOf(AtributosHumanoRepository.class);

        Atributo nombre = Atributo.create("Nombre", TipoAtributo.OBLIGATORIO, TipoCampoAtributo.TEXT);
        Atributo apellido = Atributo.create("Apellido", TipoAtributo.OBLIGATORIO, TipoCampoAtributo.TEL);
        Atributo nacimiento = Atributo.create("Nacimiento", TipoAtributo.OPCIONAL, TipoCampoAtributo.DATE);
        Atributo direccion = Atributo.create("Direccion", TipoAtributo.OPCIONAL, TipoCampoAtributo.TEXT);
        Atributo provincia = Atributo.create("Provincia", TipoAtributo.OPCIONAL, TipoCampoAtributo.TEXT);
        Atributo email = Atributo.create("Mail", TipoAtributo.OPCIONAL, TipoCampoAtributo.EMAIL);
        Atributo wpp = Atributo.create("WhatsApp", TipoAtributo.OPCIONAL, TipoCampoAtributo.TEL);
        Atributo telegram = Atributo.create("Telegram", TipoAtributo.OPCIONAL,TipoCampoAtributo.TEL);

        atributos.guardar(nombre);
        atributos.guardar(apellido);
        atributos.guardar(nacimiento);
        atributos.guardar(direccion);
        atributos.guardar(provincia);
        atributos.guardar(email);
        atributos.guardar(wpp);
        atributos.guardar(telegram);

    }
}
