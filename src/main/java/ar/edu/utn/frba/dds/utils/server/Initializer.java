package ar.edu.utn.frba.dds.utils.server;

import ar.edu.utn.frba.dds.dtos.direccion.DireccionInputDTO;
import ar.edu.utn.frba.dds.dtos.humanos.HumanoInputDTO;
import ar.edu.utn.frba.dds.dtos.humanos.HumanoOutputDTO;
import ar.edu.utn.frba.dds.dtos.juridico.JuridicoInputDTO;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.*;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Incidente;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.TipoEvento;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.sensores_y_receptores.SensorDeMovimiento;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.sensores_y_receptores.SensorTemperatura;
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
import ar.edu.utn.frba.dds.models.repositories.tarjetas_vulnerables.TarjetasVulnerablesRepository;
import ar.edu.utn.frba.dds.models.repositories.usuarios.UsuariosRepository;
import ar.edu.utn.frba.dds.services.receptores.ReceptorMovimiento;
import ar.edu.utn.frba.dds.services.receptores.ReceptorTemperatura;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import ar.edu.utn.frba.dds.utils.seguridad.HashPassword;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Initializer {
    public static void init() {
        HeladerasRepository heladeras = ServiceLocator.instanceOf(HeladerasRepository.class);
        HumanosRepository humanos = ServiceLocator.instanceOf(HumanosRepository.class);
        JuridicasRepository juridicas = ServiceLocator.instanceOf(JuridicasRepository.class);
        DistribucionesDeViandasRepository distribuciones = ServiceLocator.instanceOf(DistribucionesDeViandasRepository.class);
        DonacionesDeViandaRepository donaciones = ServiceLocator.instanceOf(DonacionesDeViandaRepository.class);
        IncidentesRepository incidentesRepository = ServiceLocator.instanceOf(IncidentesRepository.class);
        UsuariosRepository usuariosRepository = ServiceLocator.instanceOf(UsuariosRepository.class);
        OfertasRepository ofertasRepository = ServiceLocator.instanceOf(OfertasRepository.class);
        RubrosRepository rubrosRepository = ServiceLocator.instanceOf(RubrosRepository.class);
        TarjetasVulnerablesRepository tarjetasVulnerablesRepository = ServiceLocator.instanceOf(TarjetasVulnerablesRepository.class);

        // creacion de heladeras

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
                .tempMaxima(7.2)
                .tempMinima(-4.1)
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
                .tempMaxima(9.8)
                .tempMinima(-5.4)
                .suscriptores(new ArrayList<>())
                .activa(true)
                .build();

        Heladera h3 = Heladera
                .builder()
                .nombre("Heladera 3")
                .direccion(d3)
                .capacidadMaxima(20)
                .capActual(18)
                .tempMaxima(8.4)
                .tempMinima(-3.1)
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
                .tempMaxima(9.8)
                .tempMinima(-5.4)
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
                .tempMaxima(9.8)
                .tempMinima(-5.4)
                .viandasColocadas(12)
                .viandasRetiradas(5)
                .suscriptores(new ArrayList<>())
                .activa(true)
                .build();

        heladeras.guardar(h1);
        heladeras.guardar(h2);
        heladeras.guardar(h3);
        heladeras.guardar(h4);
        heladeras.guardar(h5);
        /*
        SensorTemperatura sensorTemp1 = new SensorTemperatura(h1.getId());
        SensorTemperatura sensorTemp2 = new SensorTemperatura(h2.getId());
        SensorTemperatura sensorTemp3 = new SensorTemperatura(h3.getId());
        SensorTemperatura sensorTemp4 = new SensorTemperatura(h4.getId());
        SensorTemperatura sensorTemp5 = new SensorTemperatura(h5.getId());

        ReceptorTemperatura receptorTemp = ReceptorTemperatura.create(ServiceLocator.instanceOf(HeladerasRepository.class));


        SensorDeMovimiento sensorMov1 = new SensorDeMovimiento(h1.getId());
        SensorDeMovimiento sensorMov2 = new SensorDeMovimiento(h2.getId());
        SensorDeMovimiento sensorMov3 = new SensorDeMovimiento(h3.getId());
        SensorDeMovimiento sensorMov4 = new SensorDeMovimiento(h4.getId());
        SensorDeMovimiento sensorMov5 = new SensorDeMovimiento(h5.getId());

        ReceptorMovimiento receptorMov = ReceptorMovimiento.create(ServiceLocator.instanceOf(HeladerasRepository.class));

        sensorMov1.enviarMovimiento();

        */

        Initializer.inicializarAtributos();

        // creacion de usuarios

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

        // creacion de atributos juridica
        TipoContacto tipoContacto = new TipoContacto("WhatsApp");
        Contacto contacto = new Contacto(tipoContacto,"1149678345");
        List<Contacto> listaDeContactos = new ArrayList<>();
        listaDeContactos.add(contacto);

        JuridicoInputDTO juridicoInputDTO = new JuridicoInputDTO(u3, "Razon Social", Tipo.EMPRESA, "Rubro 1" ,listaDeContactos, d3);

        ServiceLocator.instanceOf(JuridicasRepository.class).guardar(Juridica.create(juridicoInputDTO));

        //creamos los tributos posibles
        Atributo nombre = ServiceLocator.instanceOf(AtributosHumanoRepository.class).buscarPorNombre("Nombre").get();
        Atributo apellido = ServiceLocator.instanceOf(AtributosHumanoRepository.class).buscarPorNombre("Apellido").get();
        Atributo nacimiento = ServiceLocator.instanceOf(AtributosHumanoRepository.class).buscarPorNombre("Nacimiento").get();
        Atributo mail = ServiceLocator.instanceOf(AtributosHumanoRepository.class).buscarPorNombre("Mail").get();
        Atributo direccion = ServiceLocator.instanceOf(AtributosHumanoRepository.class).buscarPorNombre("Direccion").get();
        Atributo provincia = ServiceLocator.instanceOf(AtributosHumanoRepository.class).buscarPorNombre("Provincia").get();
        Atributo wpp = ServiceLocator.instanceOf(AtributosHumanoRepository.class).buscarPorNombre("WhatsApp").get();
        Atributo telegram = ServiceLocator.instanceOf(AtributosHumanoRepository.class).buscarPorNombre("Telegram").get();

        AtributoHumanoRespondido nombre_respondido1 = new AtributoHumanoRespondido("Pedro", nombre);
        AtributoHumanoRespondido apellido_respondido1 = new AtributoHumanoRespondido("Perez", apellido);
        AtributoHumanoRespondido email_respondido1 = new AtributoHumanoRespondido("", mail);
        AtributoHumanoRespondido nacimiento_respondido1 = new AtributoHumanoRespondido("1990-01-01", nacimiento);
        AtributoHumanoRespondido direccion_respondido1 = new AtributoHumanoRespondido("", direccion);
        AtributoHumanoRespondido provincia_respondido1 = new AtributoHumanoRespondido("", provincia);
        AtributoHumanoRespondido wss_respondido1 = new AtributoHumanoRespondido("1147379856", wpp);
        AtributoHumanoRespondido telegram_respondido1 = new AtributoHumanoRespondido("", telegram);

        List<AtributoHumanoRespondido> obligatorios = List.of(nombre_respondido1, apellido_respondido1,wss_respondido1);
        List<AtributoHumanoRespondido> opcionales = List.of(nacimiento_respondido1,email_respondido1,direccion_respondido1,provincia_respondido1 , telegram_respondido1);

        // creacion de atributos colaboradores humanos

        HumanoInputDTO inputDTO = new HumanoInputDTO(obligatorios, List.of("Mail","WhatsApp","Telegram"),opcionales,new ArrayList<>(), u1, d1);

        ColaboradorHumano c1 = ColaboradorHumano.create(inputDTO);

        AtributoHumanoRespondido nombre_respondido2 = new AtributoHumanoRespondido("Luquitas", nombre);
        AtributoHumanoRespondido apellido_respondido2 = new AtributoHumanoRespondido("Perez", apellido);
        AtributoHumanoRespondido email_respondido = new AtributoHumanoRespondido("facu@gmail.com", mail);
        AtributoHumanoRespondido nacimiento_respondido2 = new AtributoHumanoRespondido("1992-01-01", nacimiento);
        AtributoHumanoRespondido direccion_respondido = new AtributoHumanoRespondido("", direccion);
        AtributoHumanoRespondido provincia_respondido = new AtributoHumanoRespondido("", provincia);
        AtributoHumanoRespondido wss_respondido = new AtributoHumanoRespondido("", wpp);
        AtributoHumanoRespondido telegram_respondido = new AtributoHumanoRespondido("", telegram);

        List<AtributoHumanoRespondido> obligatorios2 = List.of(nombre_respondido2, apellido_respondido2,email_respondido);
        List<AtributoHumanoRespondido> opcionales2 = List.of(nacimiento_respondido2,wss_respondido,telegram_respondido,provincia_respondido,direccion_respondido);

        HumanoInputDTO inputDTO2 = new HumanoInputDTO(obligatorios2,List.of("Mail","WhatsApp","Telegram"), opcionales2,new ArrayList<>() , u2, d2);

        ColaboradorHumano c2 = ColaboradorHumano.create(inputDTO2);

        // creacion de tarjetas

        TarjetaPersonaVulnerable tarjeta1 = new TarjetaPersonaVulnerable(); // 100
        TarjetaPersonaVulnerable tarjeta2 = new TarjetaPersonaVulnerable(); // 101
        TarjetaPersonaVulnerable tarjeta3 = new TarjetaPersonaVulnerable(); // 102
        TarjetaPersonaVulnerable tarjeta4 = new TarjetaPersonaVulnerable(); // 103
        TarjetaPersonaVulnerable tarjeta5 = new TarjetaPersonaVulnerable(); // 104
        TarjetaPersonaVulnerable tarjeta6 = new TarjetaPersonaVulnerable(); // 105

        tarjetasVulnerablesRepository.guardar(tarjeta1);
        tarjetasVulnerablesRepository.guardar(tarjeta2);
        tarjetasVulnerablesRepository.guardar(tarjeta3);
        tarjetasVulnerablesRepository.guardar(tarjeta4);
        tarjetasVulnerablesRepository.guardar(tarjeta5);
        tarjetasVulnerablesRepository.guardar(tarjeta6);

        // creacion de donaciones y distribuciones

        c2.setPuntosGanados(2500.0);//funciona :)
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

        // creacion de incidentes

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

        // creacion de rubros

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

        // creacion de ofertas
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
