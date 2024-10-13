package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.exceptions.HeladeraInexistenteException;
import ar.edu.utn.frba.dds.exceptions.SolicitudIncorrectaException;
import ar.edu.utn.frba.dds.exceptions.donacionDinero.MontoInvalidoException;
import ar.edu.utn.frba.dds.exceptions.registroPersonaVulnerable.RegistroTarjetaInexistenteException;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.*;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.carga_masiva.CargaMasiva;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.carga_masiva.ConversorCSVReader;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.helpers.conversor_json.ConversorJSON;
import ar.edu.utn.frba.dds.models.entities.helpers.json_to_entidad.JSONtoOferta;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import ar.edu.utn.frba.dds.models.entities.personas.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.models.repositories.donacion_dinero.DonacionDineroRepository;
import ar.edu.utn.frba.dds.models.repositories.ofertas.OfertasRepository;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.juridicas.JuridicasRepository;
import ar.edu.utn.frba.dds.models.repositories.personasVulnerables.PersonasVulnerablesRepository;
import ar.edu.utn.frba.dds.models.repositories.tarjetas_vulnerables.TarjetasVulnerablesRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import ar.edu.utn.frba.dds.utils.permisos.PermisoDenegadoException;
import ar.edu.utn.frba.dds.utils.permisos.VerificadorDePermisos;
import com.fasterxml.jackson.databind.JsonNode;
import io.javalin.http.Context;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ContribucionesController {
    private HumanosRepository humanos;
    private HeladerasRepository heladeras;
    private TarjetasVulnerablesRepository tarjetas;
    private JuridicasRepository juridicas;
    private OfertasRepository ofertas;


    @SneakyThrows
    public void crearDonacionDeViandas(String json) {
        JsonNode node = ConversorJSON.convertir(json);
        Long id = Long.parseLong(node.get("id_usuario").asText());

        Optional<ColaboradorHumano> humano = humanos.buscarPorIdUsuario(id);

        if (humano.isPresent()) {
            ColaboradorHumano h = humano.get();

            VerificadorDePermisos.tienePermiso(h.getUser(), "DONAR_VIANDAS");

            String heladera_destino = node.get("heladera_destino").asText();
            Optional<Heladera> destino = heladeras.buscarPorNombre(heladera_destino);

            if (destino.isPresent()) {
                Heladera heladera = destino.get();
                DonacionDeVianda donacion = ContribucionHumanaFactory.crearDonacionDeVianda(h,heladera);
                heladera.agregarViandas(1);
                h.sumarPuntaje(donacion);
                return;
            }
            throw new HeladeraInexistenteException("No se encontró la heladera");
        } else {
            throw new PermisoDenegadoException("Debes tener una cuenta para realizar esta acción");
        }
    }

    public void crearDistribucionDeViandas(String json) {
        JsonNode node = ConversorJSON.convertir(json);
        Long id = Long.parseLong(node.get("id_usuario").asText());

        Optional<ColaboradorHumano> posibleHumano = humanos.buscarPorIdUsuario(id);

        if (posibleHumano.isPresent()) {
            ColaboradorHumano h = posibleHumano.get();

            VerificadorDePermisos.tienePermiso(h.getUser(), "DISTRIBUIR_VIANDAS");

            String punto_origen = node.get("heladera_origen").asText();
            String punto_destino = node.get("heladera_destino").asText();
            Integer cantidad = node.get("cantidad").asInt();
            String motivo = node.get("motivo").asText();

            Optional<Heladera> origen = heladeras.buscarPorNombre(punto_origen);
            Optional<Heladera> destino = heladeras.buscarPorNombre(punto_destino);

            if (origen.isPresent() && destino.isPresent()) {
                Heladera hOrigen = origen.get();
                Heladera hDestino = destino.get();

                hOrigen.quitarViandas(cantidad);
                hDestino.agregarViandas(cantidad);

                DistribucionViandas distri = ContribucionHumanaFactory.crearDistribucionDeViandas(hOrigen, hDestino, cantidad, motivo, h);
                h.sumarPuntaje(distri);
                return;
            }
            throw new HeladeraInexistenteException("No se encontró alguna de las heladeras");

        } else {
            throw new PermisoDenegadoException("Debes tener una cuenta para realizar esta acción");
        }
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
                Direccion.of(domicilio, ""), // que pongo en provincia?
                dni,
                Integer. valueOf(menoresACargo),
                List.of(tarjeta)
                );

        PersonasVulnerablesRepository personasVulnerablesRepository = ServiceLocator.instanceOf(PersonasVulnerablesRepository.class);
        personasVulnerablesRepository.guardar(personaVulnerable);

        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Colaboración confirmada");

        ctx.render("colaboraciones/confirmacion-colaboracion.hbs", model);
    }

    public void registrarHeladeraACargo(String json) {
        JsonNode node = ConversorJSON.convertir(json);
        Long id = Long.parseLong(node.get("id_usuario").asText());
        String heladera = node.get("heladera").asText();

        Optional<Juridica> posibleJuridica = juridicas.buscarPorIdUsuario(id);
        Optional<Heladera> heladeraOpt = heladeras.buscarPorNombre(heladera);

        if (posibleJuridica.isPresent() && heladeraOpt.isPresent()) {
            Juridica j = posibleJuridica.get();

            VerificadorDePermisos.tienePermiso(j.getUser(), "HACERSE_CARGO_HELADERA");


            Heladera h = heladeraOpt.get();

            HacerseCargoHeladera contribucion = ContribucionJuridicaFactory.hacerseCargoHeladera(h,j);
            j.sumarPuntaje(contribucion);
        } else if (posibleJuridica.isEmpty()) {
            throw new PermisoDenegadoException("Debes tener una cuenta para realizar esta acción");
        } else {
            throw new HeladeraInexistenteException("La heladera ingresada no existe.");
        }

    }

    public void registrarOferta(String json) {
        JsonNode node = ConversorJSON.convertir(json);
        Long id = Long.parseLong(node.get("id_usuario").asText());

        Optional<Juridica> posibleJuridica = juridicas.buscarPorIdUsuario(id);


        if (posibleJuridica.isEmpty()) {
            throw new PermisoDenegadoException("Debes tener una cuenta para realizar esta acción");
        }
        Juridica juridica = posibleJuridica.get();

        VerificadorDePermisos.tienePermiso(juridica.getUser(), "HACERSE_CARGO_HELADERA");


        Oferta oferta = JSONtoOferta.convertir(node);
        OfrecerProductoOServicio contribucion = ContribucionJuridicaFactory.ofertar(oferta, juridica);
        juridica.sumarPuntaje(contribucion);
        ofertas.guardar(oferta);
    }

    public void cargaMasiva(Context ctx){
        String path = ctx.formParam("path");

        ConversorCSVReader conversorCSV = ServiceLocator.instanceOf(ConversorCSVReader.class);

        CargaMasiva cargaMasiva = new CargaMasiva(path, conversorCSV);

        cargaMasiva.cargar();

        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Colaboración confirmada");

        ctx.render("colaboraciones/confirmacion-colaboracion.hbs", model);
    }

}
