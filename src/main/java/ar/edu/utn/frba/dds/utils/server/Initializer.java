package ar.edu.utn.frba.dds.utils.server;

import ar.edu.utn.frba.dds.dtos.direccion.DireccionInputDTO;
import ar.edu.utn.frba.dds.dtos.humanos.HumanoInputDTO;
import ar.edu.utn.frba.dds.dtos.humanos.HumanoOutputDTO;
import ar.edu.utn.frba.dds.dtos.juridico.JuridicoInputDTO;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.*;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Incidente;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.TipoEvento;
import ar.edu.utn.frba.dds.models.entities.personas.*;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.models.entities.usuarios.TipoRol;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.entities.usuarios.Rol;
import ar.edu.utn.frba.dds.models.factories.direcciones.DireccionFactory;
import ar.edu.utn.frba.dds.models.repositories.atributos_humano.AtributosHumanoRepository;
import ar.edu.utn.frba.dds.models.repositories.distribuciones_de_viandas.DistribucionesDeViandasRepository;
import ar.edu.utn.frba.dds.models.repositories.donaciones_de_vianda.DonacionesDeViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import ar.edu.utn.frba.dds.models.repositories.juridicas.JuridicasRepository;
import ar.edu.utn.frba.dds.models.repositories.ofertas.OfertasRepository;
import ar.edu.utn.frba.dds.models.repositories.rubros.RubrosRepository;
import ar.edu.utn.frba.dds.models.repositories.usuarios.UsuariosRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import ar.edu.utn.frba.dds.utils.seguridad.HashPassword;


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
        OfertasRepository ofertasRepository = ServiceLocator.instanceOf(OfertasRepository.class);
        RubrosRepository rubrosRepository = ServiceLocator.instanceOf(RubrosRepository.class);

        Direccion d1 = DireccionFactory.create(new DireccionInputDTO("Mozart 2300", "CABA"));

        Direccion d2 = DireccionFactory.create(new DireccionInputDTO("Pepiri 1234", "CABA"));

        Direccion d3 = DireccionFactory.create(new DireccionInputDTO("Avenida Triunvirato 4000", "CABA"));

        Direccion d4 = DireccionFactory.create(new DireccionInputDTO("Nazca 2000", "CABA"));

        Direccion d5 = DireccionFactory.create(new DireccionInputDTO("Avenida Medrano 951", "CABA"));

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

        Heladera h5 = Heladera
                .builder()
                .nombre("Heladera medrano")
                .direccion(d5)
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

        HashPassword hash = ServiceLocator.instanceOf(HashPassword.class);
        Usuario u1 = new Usuario("usuario1", hash.hashPassword("Pedritoclavounclavito123@") , List.of(TipoRol.ADMIN));
        u1.setId(1L);
        Usuario u2 = new Usuario("usuario2", hash.hashPassword("prueba"), List.of(TipoRol.HUMANO));
        u2.setId(2L);
        Usuario u3 = new Usuario("usuario3", hash.hashPassword("prueba"), List.of(TipoRol.JURIDICA));
        u3.setId(3L);

        usuariosRepository.guardar(u1);
        usuariosRepository.guardar(u2);
        usuariosRepository.guardar(u3);

        JuridicoInputDTO juridicoInputDTO = new JuridicoInputDTO(u3, "Razon Social", Tipo.EMPRESA, "Rubro 1" ,new ArrayList<>(), d3);

        ServiceLocator.instanceOf(JuridicasRepository.class).guardar(Juridica.create(juridicoInputDTO));

        Atributo nombre = ServiceLocator.instanceOf(AtributosHumanoRepository.class).buscarPorNombre("Nombre").get();
        Atributo apellido = ServiceLocator.instanceOf(AtributosHumanoRepository.class).buscarPorNombre("Apellido").get();
        Atributo nacimiento = ServiceLocator.instanceOf(AtributosHumanoRepository.class).buscarPorNombre("Nacimiento").get();

        AtributoHumanoRespondido nombre_respondido1 = new AtributoHumanoRespondido("Pedro", nombre);
        AtributoHumanoRespondido apellido_respondido1 = new AtributoHumanoRespondido("Perez", apellido);
        AtributoHumanoRespondido nacimiento_respondido1 = new AtributoHumanoRespondido("1990-01-01", nacimiento);

        List<AtributoHumanoRespondido> obligatorios = List.of(nombre_respondido1, apellido_respondido1);
        List<AtributoHumanoRespondido> opcionales = List.of(nacimiento_respondido1);

        HumanoInputDTO inputDTO = new HumanoInputDTO(obligatorios, new ArrayList<>(), opcionales, u1, d1);

        ColaboradorHumano c1 = ColaboradorHumano.create(inputDTO);

        AtributoHumanoRespondido nombre_respondido2 = new AtributoHumanoRespondido("Luquitas", nombre);
        AtributoHumanoRespondido apellido_respondido2 = new AtributoHumanoRespondido("Perez", apellido);
        AtributoHumanoRespondido nacimiento_respondido2 = new AtributoHumanoRespondido("1992-01-01", nacimiento);

        List<AtributoHumanoRespondido> obligatorios2 = List.of(nombre_respondido2, apellido_respondido2);
        List<AtributoHumanoRespondido> opcionales2 = List.of(nacimiento_respondido2);

        HumanoInputDTO inputDTO2 = new HumanoInputDTO(obligatorios2, new ArrayList<>(), opcionales2, u2, d2);

        ColaboradorHumano c2 = ColaboradorHumano.create(inputDTO2);
        c2.setPuntosGanados(2500.0); //funciona :)
        c2.generarContacto(new Contacto(new TipoContacto("email"),"facu@gmail.com"));
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

        Rubro peluqueria = new Rubro("Peluqueria");
        Rubro tecnologia = new Rubro("Tecnologia");
        Rubro deportivo = new Rubro("Deportivo");
        Rubro indumentaria = new Rubro("Indumentaria");
        Rubro gastronomia = new Rubro("Gastronomia");
        Rubro viajes = new Rubro("Viajes");
        Rubro educacion = new Rubro("Educacion");

        rubrosRepository.guardar(peluqueria);
        rubrosRepository.guardar(tecnologia);
        rubrosRepository.guardar(deportivo);
        rubrosRepository.guardar(indumentaria);
        rubrosRepository.guardar(gastronomia);
        rubrosRepository.guardar(viajes);
        rubrosRepository.guardar(educacion);

        Oferta oferta1 = Oferta.builder()
                        .nombre("holaaaa")
                        .puntosNecesarios(1000.0)
                        .rubro(peluqueria)
                        .canjesTotales(200)
                        .canjesUsados(0)
                        .image("imagenes/caridad.jpg")
                        .build();
        oferta1.setId(1L);
        oferta1.setPresente(true);

        Oferta oferta2 = Oferta.builder()
                .nombre("Corte de Cabello Premium")
                .puntosNecesarios(800.0)
                .rubro(peluqueria)
                .canjesTotales(150)
                .canjesUsados(50)
                .image("imagenes/caridad.jpg")
                .build();
        oferta2.setId(2L);
        oferta2.setPresente(true);

        Oferta oferta3 = Oferta.builder()
                .nombre("Laptop Gaming")
                .puntosNecesarios(5000.0)
                .rubro(tecnologia)
                .canjesTotales(100)
                .canjesUsados(25)
                .image("imagenes/caridad.jpg")
                .build();
        oferta3.setId(3L);
        oferta3.setPresente(true);

        Oferta oferta4 = Oferta.builder()
                .nombre("Entrenamiento Personalizado")
                .puntosNecesarios(1200.0)
                .rubro(deportivo)
                .canjesTotales(80)
                .canjesUsados(20)
                .image("imagenes/caridad.jpg")
                .build();
        oferta4.setId(4L);
        oferta4.setPresente(true);

        Oferta oferta5 = Oferta.builder()
                .nombre("Conjunto Deportivo")
                .puntosNecesarios(600.0)
                .rubro(indumentaria)
                .canjesTotales(300)
                .canjesUsados(100)
                .image("imagenes/caridad.jpg")
                .build();
        oferta5.setId(5L);
        oferta5.setPresente(true);

        Oferta oferta6 = Oferta.builder()
                .nombre("Cena para Dos en Restaurante Gourmet")
                .puntosNecesarios(1500.0)
                .rubro(gastronomia)
                .canjesTotales(50)
                .canjesUsados(10)
                .image("imagenes/caridad.jpg")
                .build();
        oferta6.setId(6L);
        oferta6.setPresente(true);

        Oferta oferta7 = Oferta.builder()
                .nombre("Paquete de Viaje a la Playa")
                .puntosNecesarios(7000.0)
                .rubro(viajes)
                .canjesTotales(30)
                .canjesUsados(5)
                .image("imagenes/caridad.jpg")
                .build();
        oferta7.setId(7L);
        oferta7.setPresente(true);

        Oferta oferta8 = Oferta.builder()
                .nombre("Curso Online de Programación")
                .puntosNecesarios(2000.0)
                .rubro(educacion)
                .canjesTotales(120)
                .canjesUsados(40)
                .image("imagenes/caridad.jpg")
                .build();
        oferta8.setId(8L);
        oferta8.setPresente(false);

        ofertasRepository.guardar(oferta1);
        ofertasRepository.guardar(oferta2);
        ofertasRepository.guardar(oferta3);
        ofertasRepository.guardar(oferta4);
        ofertasRepository.guardar(oferta5);
        ofertasRepository.guardar(oferta6);
        ofertasRepository.guardar(oferta7);
        ofertasRepository.guardar(oferta8);


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
