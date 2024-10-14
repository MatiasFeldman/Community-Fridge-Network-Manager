package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.exceptions.DistribucionViandas.CantidadViandasIncorrectaException;
import ar.edu.utn.frba.dds.exceptions.DistribucionViandas.MismaHeladeraException;
import ar.edu.utn.frba.dds.exceptions.HeladeraInexistenteException;
import ar.edu.utn.frba.dds.exceptions.SolicitudIncorrectaException;
import ar.edu.utn.frba.dds.exceptions.donacionDinero.MontoInvalidoException;
import ar.edu.utn.frba.dds.exceptions.registroHeladera.CapacidadIncorrectaException;
import ar.edu.utn.frba.dds.exceptions.registroHeladera.TemperaturaIncorrectaException;
import ar.edu.utn.frba.dds.exceptions.registroPersonaVulnerable.FechaNacimientoIncorrectaException;
import ar.edu.utn.frba.dds.exceptions.registroPersonaVulnerable.MenoresACargoIncorrectoException;
import ar.edu.utn.frba.dds.exceptions.registroPersonaVulnerable.RegistroTarjetaInexistenteException;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.*;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.carga_masiva.CargaMasiva;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.carga_masiva.ConversorCSVReader;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.helpers.conversor_json.ConversorJSON;
import ar.edu.utn.frba.dds.models.entities.helpers.json_to_entidad.JSONtoOferta;
import ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos.APIRecomendadoraDePuntos;
import ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos.molde.ListaDeUbicaciones;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import ar.edu.utn.frba.dds.models.entities.personas.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Comuna;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Provincia;
import ar.edu.utn.frba.dds.models.repositories.distribuciones_de_viandas.DistribucionesDeViandasRepository;
import ar.edu.utn.frba.dds.models.repositories.donacion_dinero.DonacionDineroRepository;
import ar.edu.utn.frba.dds.models.repositories.donaciones_de_vianda.DonacionesDeViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.ofertas.OfertasRepository;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.juridicas.JuridicasRepository;
import ar.edu.utn.frba.dds.models.repositories.ofrecerProducto.OfrecerProductoRepository;
import ar.edu.utn.frba.dds.models.repositories.personasVulnerables.PersonasVulnerablesRepository;
import ar.edu.utn.frba.dds.models.repositories.tarjetas_vulnerables.TarjetasVulnerablesRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import ar.edu.utn.frba.dds.utils.permisos.PermisoDenegadoException;
import ar.edu.utn.frba.dds.utils.permisos.VerificadorDePermisos;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import lombok.SneakyThrows;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ContribucionesController {

    public void crearDonacionDeViandas(Context ctx) {
        String heladeraId = ctx.formParam("heladera");

        if (heladeraId == null) {
            throw new SolicitudIncorrectaException();
        }

        Long heladeraIdLong;
        try {
            heladeraIdLong = Long.parseLong(heladeraId);
        } catch (NumberFormatException e) {
            throw new SolicitudIncorrectaException();
        }

        HeladerasRepository heladerasRepository = ServiceLocator.instanceOf(HeladerasRepository.class);
        Optional<Heladera> posibleHeladera = heladerasRepository.buscarPorId(heladeraIdLong);

        if (posibleHeladera.isEmpty()) {
            throw new SolicitudIncorrectaException();
        }

        Heladera heladera = posibleHeladera.get();

        if (heladera.getCapActual() < 0) {
            throw new SolicitudIncorrectaException();
        }

        Long userId = ctx.sessionAttribute("id");
        HumanosRepository humanosRepository = ServiceLocator.instanceOf(HumanosRepository.class);
        Optional<ColaboradorHumano> posibleColaboradorHumano = humanosRepository.buscarPorIdUsuario(userId);

        if (posibleColaboradorHumano.isEmpty()) {
            throw new SolicitudIncorrectaException();
        }

        ColaboradorHumano colaboradorHumano = posibleColaboradorHumano.get();

        DonacionDeVianda donacionDeVianda = DonacionDeVianda.of(heladera, colaboradorHumano);

        DonacionesDeViandaRepository donacionesDeViandaRepository = ServiceLocator.instanceOf(DonacionesDeViandaRepository.class);
        donacionesDeViandaRepository.guardar(donacionDeVianda);

        heladera.agregarViandas(1);

        colaboradorHumano.sumarPuntaje(donacionDeVianda);

        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Colaboración confirmada");

        ctx.render("colaboraciones/confirmacion-colaboracion.hbs", model);
    }

    public void crearDistribucionDeViandas(Context ctx) {
        String heladeraOrigenId = ctx.formParam("heladeraOrigen");
        String heladeraDestinoId = ctx.formParam("heladeraDestino");
        String cantidadViandas = ctx.formParam("cantidadViandas");
        String motivoDistribucion = ctx.formParam("motivoDistribucion");

        if (heladeraOrigenId == null || heladeraDestinoId == null || cantidadViandas == null || motivoDistribucion == null) {
            throw new SolicitudIncorrectaException();
        }

        if (heladeraOrigenId.equals(heladeraDestinoId)) {
            throw new MismaHeladeraException("La heladera origen y la heladera destino no pueden ser la misma");
        }

        Long heladeraOrigenIdLong;
        Long heladeraDestinoIdLong;
        Integer cantidadViandasInt;
        try {
            heladeraOrigenIdLong = Long.parseLong(heladeraOrigenId);
            heladeraDestinoIdLong = Long.parseLong(heladeraDestinoId);
            cantidadViandasInt = Integer.parseInt(cantidadViandas);
        } catch (NumberFormatException e) {
            throw new SolicitudIncorrectaException();
        }

        HeladerasRepository heladerasRepository = ServiceLocator.instanceOf(HeladerasRepository.class);
        Optional<Heladera> posibleHeladeraOrigen = heladerasRepository.buscarPorId(heladeraOrigenIdLong);
        Optional<Heladera> posibleHeladeraDestino = heladerasRepository.buscarPorId(heladeraDestinoIdLong);

        if (posibleHeladeraOrigen.isEmpty() || posibleHeladeraDestino.isEmpty()) {
            throw new SolicitudIncorrectaException();
        }

        Heladera heladeraOrigen = posibleHeladeraOrigen.get();
        Heladera heladeraDestino = posibleHeladeraDestino.get();

        if (cantidadViandasInt < 1) {
            throw new CantidadViandasIncorrectaException("La cantidad de viandas debe ser mayor a 0");
        }

        if ((heladeraOrigen.cantActual() - cantidadViandasInt) < 0) {
            throw new CantidadViandasIncorrectaException("La heladera origen no tiene suficientes viandas");
        }

        if (heladeraDestino.cantActual() + cantidadViandasInt > heladeraDestino.getCapacidadMaxima()) {
            throw new CantidadViandasIncorrectaException("La heladera destino no tiene capacidad suficiente");
        }

        HumanosRepository humanosRepository = ServiceLocator.instanceOf(HumanosRepository.class);
        Long userId = ctx.sessionAttribute("id");
        Optional<ColaboradorHumano> posibleColaboradorHumano = humanosRepository.buscarPorIdUsuario(userId);

        if (posibleColaboradorHumano.isEmpty()) {
            throw new SolicitudIncorrectaException();
        }

        ColaboradorHumano colaboradorHumano = posibleColaboradorHumano.get();

        DistribucionViandas distribucionDeViandas = DistribucionViandas.of(heladeraOrigen, heladeraDestino, cantidadViandasInt, motivoDistribucion, colaboradorHumano);

        heladeraOrigen.quitarViandas(cantidadViandasInt);
        heladeraDestino.agregarViandas(cantidadViandasInt);

        colaboradorHumano.sumarPuntaje(distribucionDeViandas);

        DistribucionesDeViandasRepository distribucionesDeViandasRepository = ServiceLocator.instanceOf(DistribucionesDeViandasRepository.class);
        distribucionesDeViandasRepository.guardar(distribucionDeViandas);

        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Colaboración confirmada");
        ctx.render("colaboraciones/confirmacion-colaboracion.hbs", model);
    }

    public void crearDonacionDeDinero(Context ctx) {
        Long userId = ctx.sessionAttribute("user");
        List<String> roles = ctx.sessionAttribute("roles");
        String frecuenciaTexto = ctx.formParam("frecuencia");
        String monto = (ctx.formParam("monto"));

        if (frecuenciaTexto == null || monto == null || userId == null || roles == null || monto.isEmpty() || frecuenciaTexto.isEmpty() || roles.isEmpty()) {
            throw new SolicitudIncorrectaException();
        }

        Double montoDouble;
        try {
            montoDouble = Double.parseDouble(monto);
        } catch (NumberFormatException e) {
            throw new MontoInvalidoException(); // monto debe ser un numero
        }

        if (montoDouble <= 0) {
            throw new MontoInvalidoException(); // monto debe ser mayor a cero
        }

        Integer frecuencia;
        ChronoUnit unidad = null;

        if (frecuenciaTexto.equals("mensual")){
            frecuencia = 1;
            unidad = ChronoUnit.MONTHS;
        } else if (frecuenciaTexto.equals("unica")){
            frecuencia = 0;
        } else {
            throw new SolicitudIncorrectaException();
        }

        DonacionDeDinero donacion;

        if (roles.contains("HUMANO")) {
            HumanosRepository humanosRepository = ServiceLocator.instanceOf(HumanosRepository.class);
            Optional<ColaboradorHumano> posibleColaboradorHumano = humanosRepository.buscarPorIdUsuario(userId);

            if (posibleColaboradorHumano.isEmpty()) {
                throw new SolicitudIncorrectaException();
            }

            ColaboradorHumano colaboradorHumano = posibleColaboradorHumano.get();

            if (frecuencia > 0){
                donacion = DonacionDeDinero.of(colaboradorHumano, montoDouble, unidad, frecuencia);
            } else {
                donacion = DonacionDeDinero.of(colaboradorHumano, montoDouble);
            }

            colaboradorHumano.sumarPuntaje(donacion);
        } else if (roles.contains("JURIDICA")) {
            JuridicasRepository juridicasRepository = ServiceLocator.instanceOf(JuridicasRepository.class);
            Optional<Juridica> posibleJuridica = juridicasRepository.buscarPorIdUsuario(userId);

            if (posibleJuridica.isEmpty()) {
                throw new SolicitudIncorrectaException();
            }

            Juridica juridica = posibleJuridica.get();

            if (frecuencia > 0){
                donacion = DonacionDeDinero.of(juridica, montoDouble, unidad, frecuencia);
            } else {
                donacion = DonacionDeDinero.of(juridica, montoDouble);
            }

            juridica.sumarPuntaje(donacion);
        } else {
            throw new SolicitudIncorrectaException();
        }

        DonacionDineroRepository donacionDineroRepository = ServiceLocator.instanceOf(DonacionDineroRepository.class);
        donacionDineroRepository.guardar(donacion);

        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Colaboración confirmada");
        ctx.render("colaboraciones/confirmacion-colaboracion.hbs", model);
    }

    public void registrarPersonaVulnerable(Context ctx) {
        String nombre = ctx.formParam("nombre");
        String fechaNacimiento = ctx.formParam("fechaNacimiento");
        String domicilio = ctx.formParam("domicilio");
        String dni = ctx.formParam("dni");
        String menoresACargo = ctx.formParam("menoresACargo");
        String numeroTarjeta = ctx.formParam("numeroTarjeta");

        if (nombre == null || fechaNacimiento == null || domicilio == null || dni == null || menoresACargo == null || numeroTarjeta == null
                || nombre.isEmpty() || fechaNacimiento.isEmpty() || domicilio.isEmpty() || dni.isEmpty() || menoresACargo.isEmpty() || numeroTarjeta.isEmpty()) {
            throw new SolicitudIncorrectaException();
        }

        Integer cantMenoresACargo;

        try {
            cantMenoresACargo = Integer. valueOf(menoresACargo);;
        } catch (NumberFormatException e) {
            throw new MenoresACargoIncorrectoException(); // menoresACargo debe ser un numero
        }

        if (cantMenoresACargo < 0) {
            throw new MenoresACargoIncorrectoException(); // menoresACargo debe ser un numero positivo
        }

        LocalDate fechaNacimientoDate = LocalDate.parse(fechaNacimiento);
        LocalDate fechaActual = LocalDate.now();

        if (fechaNacimientoDate.isAfter(fechaActual) || fechaNacimientoDate.isBefore(fechaActual.minusYears(120))) {
            throw new FechaNacimientoIncorrectaException(); // fecha de nacimiento debe ser entre 120 años antes de la fecha actual y la fecha actual
        }

        Long userId = ctx.sessionAttribute("id");

        HumanosRepository humanosRepository = ServiceLocator.instanceOf(HumanosRepository.class);
        Optional<ColaboradorHumano> posibleColaboradorHumano = humanosRepository.buscarPorIdUsuario(userId);

        if (posibleColaboradorHumano.isEmpty()) {
            throw new SolicitudIncorrectaException();
        }

        ColaboradorHumano colaboradorHumano = posibleColaboradorHumano.get();

        TarjetasVulnerablesRepository tarjetasVulnerablesRepository = ServiceLocator.instanceOf(TarjetasVulnerablesRepository.class);
        Optional<TarjetaPersonaVulnerable> posibleTarjeta = tarjetasVulnerablesRepository.buscarPorId(Long.valueOf(numeroTarjeta));

        if (posibleTarjeta.isEmpty()) {
            throw new RegistroTarjetaInexistenteException();
        }

        TarjetaPersonaVulnerable tarjeta = posibleTarjeta.get();

        PersonaVulnerable personaVulnerable = new PersonaVulnerable(
                colaboradorHumano,
                nombre,
                LocalDate.parse(fechaNacimiento),
                LocalDate.now(),
                Direccion.of(domicilio, "CABA"),
                dni,
                cantMenoresACargo,
                List.of(tarjeta)
                );

        PersonasVulnerablesRepository personasVulnerablesRepository = ServiceLocator.instanceOf(PersonasVulnerablesRepository.class);
        personasVulnerablesRepository.guardar(personaVulnerable);

        RegistroPersonaVulnerable registroPersonaVulnerable = RegistroPersonaVulnerable.of(tarjeta, colaboradorHumano);

        // todo: agregar al repo

        colaboradorHumano.sumarPuntaje(registroPersonaVulnerable);

        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Colaboración confirmada");

        ctx.render("colaboraciones/confirmacion-colaboracion.hbs", model);
    }

    public void registrarHeladeraACargo(Context ctx) {
        String nombre = ctx.formParam("nombre");
        String capacidadMaximaStr = ctx.formParam("capacidadMaxima");
        String calle = ctx.formParam("calle");
        String comuna = ctx.formParam("comuna");
        String temperaturaMaximaStr = ctx.formParam("temperaturaMaxima");
        String temperaturaMinimaStr = ctx.formParam("temperaturaMinima");

        if (nombre == null || capacidadMaximaStr == null || calle == null || comuna == null ||
                temperaturaMaximaStr == null || temperaturaMinimaStr == null) {
            throw new SolicitudIncorrectaException();
        }

        Integer capacidadMaxima;
        Double temperaturaMaxima, temperaturaMinima;

        try {
            capacidadMaxima = Integer.parseInt(capacidadMaximaStr);
            temperaturaMaxima = Double.parseDouble(temperaturaMaximaStr);
            temperaturaMinima = Double.parseDouble(temperaturaMinimaStr);
        } catch (NumberFormatException e) {
            throw new SolicitudIncorrectaException();
        }

        if (capacidadMaxima < 1 || capacidadMaxima > 100) {
            throw new CapacidadIncorrectaException();
        }

        if (temperaturaMaxima < temperaturaMinima || temperaturaMaxima >= 8 || temperaturaMinima < -18) {
            throw new TemperaturaIncorrectaException();
        }

        Long userId = ctx.sessionAttribute("id");

        JuridicasRepository juridicasRepository = ServiceLocator.instanceOf(JuridicasRepository.class);
        Optional<Juridica> posibleJuridica = juridicasRepository.buscarPorIdUsuario(userId);

        if (posibleJuridica.isEmpty()) {
            throw new SolicitudIncorrectaException();
        }

        Juridica juridica = posibleJuridica.get();

        Heladera heladera = Heladera.builder()
                .nombre(nombre)
                .capacidadMaxima(capacidadMaxima)
                .capActual(0)
                .fechaDePuestaEnFuncionamiento(LocalDate.now())
                .activa(true)
                .tempMinima(temperaturaMinima)
                .tempMaxima(temperaturaMaxima)
                .ultimaTemperaturaRegistrada(0.0)
                .ultFechaRegistrada(null)
                .viandasColocadas(0)
                .viandasRetiradas(0)
                .direccion(new Direccion(calle, new Comuna(comuna), new Provincia("CABA"), null)) // TODO: obtener correctamente la direccion
                .build();

        HacerseCargoHeladera hacerseCargoHeladera = HacerseCargoHeladera.of(heladera, juridica);

        HeladerasRepository heladerasRepository = ServiceLocator.instanceOf(HeladerasRepository.class);
        heladerasRepository.guardar(heladera);

        juridica.sumarPuntaje(hacerseCargoHeladera);

        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Colaboración confirmada");

        ctx.render("colaboraciones/confirmacion-colaboracion.hbs", model);
    }

    public void registrarOferta(Context ctx) {
        String nombreProducto = ctx.formParam("nombreProducto");
        String puntosNecesarios = ctx.formParam("puntosNecesarios");
        String canjesTotales = ctx.formParam("canjesTotales");
        String tipoProducto = ctx.formParam("tipoProducto");

        if(nombreProducto == null || puntosNecesarios == null || canjesTotales == null || tipoProducto == null){
            throw new SolicitudIncorrectaException();
        }
        Double puntosNecesariosDouble;
        Integer canjesTotalesInt;
        try {
            puntosNecesariosDouble = Double.parseDouble(puntosNecesarios);
            canjesTotalesInt = Integer.parseInt(canjesTotales);
        } catch (NumberFormatException e) {
            throw new SolicitudIncorrectaException();
        }

        // Obtener archivo de imagen (si se cargó uno)
        UploadedFile imagenProducto = ctx.uploadedFile("imagenProducto");
        // todo: guardar imagen en el servidor

        Long userId = ctx.sessionAttribute("id");

        JuridicasRepository juridicasRepository = ServiceLocator.instanceOf(JuridicasRepository.class);
        Optional<Juridica> posibleJuridica = juridicasRepository.buscarPorIdUsuario(userId);

        if (posibleJuridica.isEmpty()) {
            throw new SolicitudIncorrectaException();
        }

        Juridica juridica = posibleJuridica.get();

        Oferta oferta = Oferta.of(nombreProducto,
                puntosNecesariosDouble,
                tipoProducto,
                canjesTotalesInt,
                null);

        OfertasRepository ofertasRepository = ServiceLocator.instanceOf(OfertasRepository.class);
        ofertasRepository.guardar(oferta);

        OfrecerProductoOServicio ofrecerProductoOServicio = OfrecerProductoOServicio.of(oferta, juridica);

        OfrecerProductoRepository ofrecerProductoRepository = ServiceLocator.instanceOf(OfrecerProductoRepository.class);
        ofrecerProductoRepository.guardar(ofrecerProductoOServicio);

        juridica.sumarPuntaje(ofrecerProductoOServicio);

        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Colaboración confirmada");
        ctx.render("colaboraciones/confirmacion-colaboracion.hbs", model);
    }

    public void cargaMasiva(Context ctx){
        String path = ctx.formParam("path");

        ConversorCSVReader conversorCSV = ServiceLocator.instanceOf(ConversorCSVReader.class);

        CargaMasiva cargaMasiva = new CargaMasiva(path, conversorCSV);

        cargaMasiva.cargar();

        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Colaboración confirmada");

        ctx.render("colaboraciones/confirmacion-colaboracion.hbs", model);

        // todo: suma puntaje??
    }

    @SneakyThrows
    public void recomendarPuntos(Context ctx) throws IOException {
        Double latitud = Double.valueOf(ctx.formParam("latitud"));
        Double longitud = Double.valueOf(ctx.formParam("longitud"));
        Double radio = Double.valueOf(ctx.formParam("radio"));

        APIRecomendadoraDePuntos apiRecomendadoraDePuntos = APIRecomendadoraDePuntos.getInstance();
        ListaDeUbicaciones ubicaciones = apiRecomendadoraDePuntos.puntosIdeales(new Coordenada(latitud,longitud),radio);
        String jsonResponse = new Gson().toJson(ubicaciones.getCoordenadas());
        /*
        List<Coordenada> coordenadas = new ArrayList<>();
        coordenadas.add(new Coordenada(123.0,123.0));
        coordenadas.add(new Coordenada(1223.0,1233.0));
        String jsonResponse = new Gson().toJson(coordenadas);*/
        ctx.json(jsonResponse);

    }

}
