package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dtos.direccion.DireccionInputDTO;
import ar.edu.utn.frba.dds.exceptions.DistribucionViandas.CantidadViandasIncorrectaException;
import ar.edu.utn.frba.dds.exceptions.DistribucionViandas.MismaHeladeraException;
import ar.edu.utn.frba.dds.exceptions.SolicitudIncorrectaException;
import ar.edu.utn.frba.dds.exceptions.donacionDinero.MontoInvalidoException;
import ar.edu.utn.frba.dds.exceptions.registroHeladera.CapacidadIncorrectaException;
import ar.edu.utn.frba.dds.exceptions.registroHeladera.TemperaturaIncorrectaException;
import ar.edu.utn.frba.dds.exceptions.registroPersonaVulnerable.FechaNacimientoIncorrectaException;
import ar.edu.utn.frba.dds.exceptions.registroPersonaVulnerable.MenoresACargoIncorrectoException;
import ar.edu.utn.frba.dds.exceptions.registroPersonaVulnerable.RegistroTarjetaInexistenteException;
import ar.edu.utn.frba.dds.exceptions.registro_usuario.TarjetaRepetidaException;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.*;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.carga_masiva.CargaMasiva;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.carga_masiva.ConversorCSVReader;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos.APIRecomendadoraDePuntos;
import ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos.molde.ListaDeUbicaciones;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import ar.edu.utn.frba.dds.models.entities.personas.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Comuna;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Provincia;
import ar.edu.utn.frba.dds.models.factories.direcciones.DireccionFactory;
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
import ar.edu.utn.frba.dds.services.georef_caba.GeorefCaba;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import ar.edu.utn.frba.dds.utils.RenderUtils;
import com.google.gson.Gson;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.mysql.cj.conf.PropertyKey.logger;

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

        RenderUtils.renderizar(ctx,"colaboraciones/confirmacion-colaboracion.hbs", model);
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
        RenderUtils.renderizar(ctx,"colaboraciones/confirmacion-colaboracion.hbs", model);
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
        RenderUtils.renderizar(ctx,"colaboraciones/confirmacion-colaboracion.hbs", model);
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

        JuridicasRepository juridicasRepository = ServiceLocator.instanceOf(JuridicasRepository.class);
        Optional<Juridica> posibleJuridica = juridicasRepository.buscarPorIdUsuario(userId);

        if (posibleJuridica.isEmpty()) {
            throw new SolicitudIncorrectaException();
        }

        Juridica juridica = posibleJuridica.get();

        TarjetasVulnerablesRepository tarjetasVulnerablesRepository = ServiceLocator.instanceOf(TarjetasVulnerablesRepository.class);
        Optional<TarjetaPersonaVulnerable> posibleTarjeta = tarjetasVulnerablesRepository.buscarPorId(Long.valueOf(numeroTarjeta));

        if (posibleTarjeta.isEmpty()) {
            throw new RegistroTarjetaInexistenteException("La tarjeta ingresada no existe");
        }

        TarjetaPersonaVulnerable tarjeta = posibleTarjeta.get();

        if (tarjeta.getDuenio() != null) {
            throw new TarjetaRepetidaException("La tarjeta ya fue registrada");
        }

        PersonaVulnerable personaVulnerable = new PersonaVulnerable(
                juridica,
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

        RegistroPersonaVulnerable registroPersonaVulnerable = RegistroPersonaVulnerable.of(tarjeta, juridica);

        tarjeta.setDuenio(personaVulnerable);

        juridica.sumarPuntaje(registroPersonaVulnerable);

        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Colaboración confirmada");

        RenderUtils.renderizar(ctx,"colaboraciones/confirmacion-colaboracion.hbs", model);
    }

    public void registrarHeladeraACargo(Context ctx) {
        String nombre = ctx.formParam("nombre");
        String capacidadMaximaStr = ctx.formParam("capacidadMaxima");
        String calle = ctx.formParam("calle");
        String provincia = ctx.formParam("provincia");
        String temperaturaMaximaStr = ctx.formParam("temperaturaMaxima");
        String temperaturaMinimaStr = ctx.formParam("temperaturaMinima");

        if (nombre == null || capacidadMaximaStr == null || calle == null || provincia == null ||
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
                .direccion(DireccionFactory.create(new DireccionInputDTO(calle, provincia)))
                .build();

        HacerseCargoHeladera hacerseCargoHeladera = HacerseCargoHeladera.of(heladera, juridica);

        HeladerasRepository heladerasRepository = ServiceLocator.instanceOf(HeladerasRepository.class);
        heladerasRepository.guardar(heladera);

        juridica.sumarPuntaje(hacerseCargoHeladera);

        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Colaboración confirmada");

        RenderUtils.renderizar(ctx,"colaboraciones/confirmacion-colaboracion.hbs", model);
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

        // Obtener archivo de imagen (si se cargó uno)
        UploadedFile imagenProducto = ctx.uploadedFile("imagenProducto");
        String rutaImagen = null;

        if (imagenProducto != null && imagenProducto.size() > 0) {
            // Definir el nombre del archivo y la ruta de almacenamiento
            String nombreArchivo = "oferta_" + oferta.getId();

            rutaImagen = "/imagenes/fotosOfertas/" + nombreArchivo + ".png";

            String directorioCompleto = Paths.get("src", "main", "resources", "public" ,rutaImagen).toString();

            // Guardar la imagen en el servidor
            try (InputStream inputStream = imagenProducto.content()) {
                Files.copy(inputStream, Paths.get(directorioCompleto), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new SolicitudIncorrectaException();
            }
        }

        oferta.setImage(rutaImagen);

        OfrecerProductoOServicio ofrecerProductoOServicio = OfrecerProductoOServicio.of(oferta, juridica);

        OfrecerProductoRepository ofrecerProductoRepository = ServiceLocator.instanceOf(OfrecerProductoRepository.class);
        ofrecerProductoRepository.guardar(ofrecerProductoOServicio);

        juridica.sumarPuntaje(ofrecerProductoOServicio);

        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Colaboración confirmada");
        RenderUtils.renderizar(ctx,"colaboraciones/confirmacion-colaboracion.hbs", model);
    }

    public void cargaMasiva(Context ctx){
        String path = ctx.formParam("path");

        ConversorCSVReader conversorCSV = ServiceLocator.instanceOf(ConversorCSVReader.class);

        CargaMasiva cargaMasiva = new CargaMasiva(path, conversorCSV);

        cargaMasiva.cargar();

        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Colaboración confirmada");

        RenderUtils.renderizar(ctx,"colaboraciones/confirmacion-colaboracion.hbs", model);

        // todo: suma puntaje??
    }

    @SneakyThrows
    public void recomendarPuntos(Context ctx) throws IOException {
        Double latitud = Double.valueOf(ctx.formParam("latitud"));
        Double longitud = Double.valueOf(ctx.formParam("longitud"));
        Double radio = Double.valueOf(ctx.formParam("radio"));

        APIRecomendadoraDePuntos apiRecomendadoraDePuntos = APIRecomendadoraDePuntos.getInstance();
        ListaDeUbicaciones ubicaciones = apiRecomendadoraDePuntos.puntosIdeales(new Coordenada(latitud,longitud),radio);

        GeorefCaba georefCaba = ServiceLocator.instanceOf(GeorefCaba.class);


        List<Direccion> direcciones = new ArrayList<>();

        for (Coordenada coordenada : ubicaciones.getCoordenadas()){
            String direccion_calle = georefCaba.getDirecc(coordenada);
            Direccion direc = DireccionFactory.create(new DireccionInputDTO(direccion_calle, "CABA"));
            direcciones.add(direc);
        }


        String jsonResponse = new Gson().toJson(direcciones);


        ctx.json(jsonResponse);

    }

}
