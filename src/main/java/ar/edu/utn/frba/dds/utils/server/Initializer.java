package ar.edu.utn.frba.dds.utils.server;

import ar.edu.utn.frba.dds.dtos.direccion.DireccionInputDTO;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.ContribucionHumanaFactory;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.DonacionDeVianda;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Incidente;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.TipoEvento;
import ar.edu.utn.frba.dds.models.entities.personas.Atributo;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.entities.personas.TipoAtributo;
import ar.edu.utn.frba.dds.models.entities.personas.TipoCampoAtributo;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.factories.direcciones.DireccionFactory;
import ar.edu.utn.frba.dds.models.repositories.atributos_humano.AtributosHumanoRepository;
import ar.edu.utn.frba.dds.models.repositories.distribuciones_de_viandas.DistribucionesDeViandasRepository;
import ar.edu.utn.frba.dds.models.repositories.donaciones_de_vianda.DonacionesDeViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import ar.edu.utn.frba.dds.models.repositories.usuarios.UsuariosRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Initializer {
    public static void init() {
        HeladerasRepository heladeras = ServiceLocator.instanceOf(HeladerasRepository.class);
        HumanosRepository humanos = ServiceLocator.instanceOf(HumanosRepository.class);
        DistribucionesDeViandasRepository distribuciones = ServiceLocator.instanceOf(DistribucionesDeViandasRepository.class);
        DonacionesDeViandaRepository donaciones = ServiceLocator.instanceOf(DonacionesDeViandaRepository.class);
        IncidentesRepository incidentesRepository = ServiceLocator.instanceOf(IncidentesRepository.class);
        UsuariosRepository usuariosRepository = ServiceLocator.instanceOf(UsuariosRepository.class);

        Direccion d1 = DireccionFactory.create(new DireccionInputDTO("Mozart 2300", "CABA"));

        Direccion d2 = DireccionFactory.create(new DireccionInputDTO("Pepiri 1234", "CABA"));

        Direccion d3 = DireccionFactory.create(new DireccionInputDTO("Avenida Triunvirato 4000", "CABA"));

        Direccion d4 = DireccionFactory.create(new DireccionInputDTO("Nazca 2000", "CABA"));

        Heladera h1 = Heladera
                .builder()
                .nombre("Heladera UTN Lugano")
                .direccion(d1)
                .capacidadMaxima(50)
                .capActual(25)
                .viandasColocadas(32)
                .viandasRetiradas(7)
                .suscriptores(new ArrayList<>())
                .activa(true)
                .build();

        Heladera h2 = Heladera
                .builder()
                .nombre("Heladera 2")
                .direccion(d2)
                .capacidadMaxima(30)
                .capActual(28)
                .viandasColocadas(22)
                .viandasRetiradas(20)
                .suscriptores(new ArrayList<>())
                .activa(true)
                .build();

        Heladera h3 = Heladera
                .builder()
                .nombre("Heladera 3")
                .direccion(d3)
                .capacidadMaxima(20)
                .capActual(18)
                .viandasColocadas(17)
                .viandasRetiradas(15)
                .suscriptores(new ArrayList<>())
                .activa(true)
                .build();

        Heladera h4 = Heladera
                .builder()
                .nombre("Heladera Flores")
                .direccion(d4)
                .capacidadMaxima(70)
                .capActual(63)
                .viandasColocadas(12)
                .viandasRetiradas(5)
                .suscriptores(new ArrayList<>())
                .activa(true)
                .build();

        heladeras.guardar(h1);
        heladeras.guardar(h2);
        heladeras.guardar(h3);
        heladeras.guardar(h4);

        Initializer.inicializarAtributos();

        Usuario u1 = new Usuario("usuario1@gmail.com", "Pedritoclavounclavito123@", null);
        u1.setId(1L);
        Usuario u2 = new Usuario("usuario2@gmail.com", "Pedritoclavounclavito123@", null);
        u2.setId(2L);

        usuariosRepository.guardar(u1);
        usuariosRepository.guardar(u2);

        ColaboradorHumano c1 = ColaboradorHumano.crearVacio();
        ColaboradorHumano c2 = ColaboradorHumano.crearVacio();
        c1.setUser(u1);
        c2.setUser(u2);

        humanos.guardar(c1);
        humanos.guardar(c2);

        DistribucionViandas distribucion1 = ContribucionHumanaFactory.crearDistribucionDeViandas(h1, h2, 5, "Motivo1", c1);
        DistribucionViandas distribucion2 = ContribucionHumanaFactory.crearDistribucionDeViandas(h2, h1, 3, "Motivo2", c2);

        distribuciones.guardar(distribucion1);
        distribuciones.guardar(distribucion2);

        DonacionDeVianda donacion1 = DonacionDeVianda.of(h1, c1);
        DonacionDeVianda donacion2 = DonacionDeVianda.of(h2, c2);

        donaciones.guardar(donacion1);
        donaciones.guardar(donacion2);

        Incidente incidente1 = Incidente.builder()
                .fecha(LocalDateTime.now())
                .heladera(h1)
                .tipo(TipoEvento.FALLA_TECNICA)
                .build();
        incidentesRepository.guardar(incidente1);

        Incidente incidente2 = Incidente.builder()
                .fecha(LocalDateTime.now())
                .heladera(h2)
                .tipo(TipoEvento.FALLA_TECNICA)
                .build();
        incidentesRepository.guardar(incidente2);

        Incidente incidente3 = Incidente.builder()
                .fecha(LocalDateTime.now())
                .heladera(h1)
                .tipo(TipoEvento.FALLA_TECNICA)
                .build();
        incidentesRepository.guardar(incidente3);

        Incidente incidente4 = Incidente.builder()
                .fecha(LocalDateTime.now())
                .heladera(h2)
                .tipo(TipoEvento.MOVIMIENTO)
                .build();
        incidentesRepository.guardar(incidente4);


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
