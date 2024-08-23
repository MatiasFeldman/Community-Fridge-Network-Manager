package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.exceptions.HeladeraInexistenteException;
import ar.edu.utn.frba.dds.exceptions.TarjetaInexistenteException;
import ar.edu.utn.frba.dds.exceptions.UsuarioSinTarjetaException;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.*;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.helpers.conversor_json.ConversorJSON;
import ar.edu.utn.frba.dds.models.entities.helpers.json_to_entidad.JSONtoDonacionDeDinero;
import ar.edu.utn.frba.dds.models.entities.helpers.json_to_entidad.JSONtoPersonaVulnerable;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import ar.edu.utn.frba.dds.models.entities.personas.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.juridicas.JuridicasRepository;
import ar.edu.utn.frba.dds.models.repositories.tarjetas.TarjetasRepository;
import ar.edu.utn.frba.dds.models.repositories.tarjetas_vulnerables.TarjetasVulnerablesRepository;
import ar.edu.utn.frba.dds.utils.permisos.PermisoDenegadoException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.SneakyThrows;

import java.util.Optional;
import java.util.UUID;

public class ContribucionesController {
    private HumanosRepository humanos;
    private HeladerasRepository heladeras;
    private TarjetasRepository tarjetas;
    private TarjetasVulnerablesRepository tarjetasVulnerables;
    private JuridicasRepository juridicas;


    @SneakyThrows
    public void crearDonacionDeViandas(String json) {
        JsonNode node = ConversorJSON.convertir(json);
        UUID id = UUID.fromString(node.get("id_usuario").asText());

        Optional<Humano> humano = humanos.buscarPorUUID(id);
        Optional<TarjetaHumano> tarjeta = tarjetas.buscarTarjetaPorDuenio(id);

        if (humano.isPresent() && tarjeta.isPresent()) {
            Humano h = humano.get();
            String heladera_destino = node.get("heladera_destino").asText();
            Optional<Heladera> destino = heladeras.buscarPorNombre(heladera_destino);


            if (destino.isPresent()) {
                Heladera heladera = destino.get();
                DonacionDeVianda donacion = ContribucionHumanaFactory.crearDonacionDeVianda(heladera, tarjeta.get());
                heladera.agregarViandas(1);
                h.agregarContribucion(donacion);
                return;
            }
            throw new HeladeraInexistenteException("No se encontró la heladera");
        } else if (humano.isEmpty()) {
            throw new PermisoDenegadoException("Debes tener una cuenta para realizar esta acción");
        } else {
            throw new UsuarioSinTarjetaException("Debes tener una tarjeta para realizar esta acción");
        }
    }

    public void crearDistribucionDeViandas(String json) {
        JsonNode node = ConversorJSON.convertir(json);
        UUID id = UUID.fromString(node.get("id_usuario").asText());

        Optional<Humano> posibleHumano = humanos.buscarPorUUID(id);
        Optional<TarjetaHumano> tarjeta = tarjetas.buscarTarjetaPorDuenio(id);

        if (posibleHumano.isPresent() && tarjeta.isPresent()) {
            Humano h = posibleHumano.get();

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

                DistribucionViandas distri = ContribucionHumanaFactory.crearDistribucionDeViandas(hOrigen, hDestino, cantidad, motivo, tarjeta.get());
                h.agregarContribucion(distri);
                return;
            }
            throw new HeladeraInexistenteException("No se encontró alguna de las heladeras");

        } else if (posibleHumano.isEmpty()) {
            throw new PermisoDenegadoException("Debes tener una cuenta para realizar esta acción");
        } else {
            throw new UsuarioSinTarjetaException("Debes tener una tarjeta para realizar esta acción");
        }
    }

    public void crearDonacionDeDinero(String json) {
        JsonNode node = ConversorJSON.convertir(json);
        UUID id = UUID.fromString(node.get("id_usuario").asText());
        String rol = node.get("rol").asText();

        if (rol.equals("HUMANO")) {
            Optional<Humano> posibleHumano = humanos.buscarPorUUID(id);
            if (posibleHumano.isPresent()) {
                Humano h = posibleHumano.get();
                DonacionDeDinero donacion = JSONtoDonacionDeDinero.convertir(node);
                h.agregarContribucion(donacion);
                return;
            }
        } else if (rol.equals("JURIDICA")) {
            Optional<Juridica> posibleJuridica = juridicas.buscarPorId(id);
            if (posibleJuridica.isPresent()) {
                Juridica j = posibleJuridica.get();
                DonacionDeDinero donacion = JSONtoDonacionDeDinero.convertir(node);
                j.agregarContribucion(donacion);
                return;
            }
        }

        throw new PermisoDenegadoException("Debes tener una cuenta para realizar esta acción");
    }

    public void registrarPersonaVulnerable(String json) {
        JsonNode node = ConversorJSON.convertir(json);
        UUID id = UUID.fromString(node.get("id_usuario").asText());
        String idTarjetaRepartida = node.get("id_tarjeta").asText();


        Optional<Humano> posibleHumano = humanos.buscarPorUUID(id);
        Optional<TarjetaPersonaVulnerable> tarjeta = tarjetasVulnerables.buscarPorId(idTarjetaRepartida);

        if (posibleHumano.isPresent() && tarjeta.isPresent()) {
            Humano h = posibleHumano.get();
            TarjetaPersonaVulnerable tarjetaPersona = tarjeta.get();

            PersonaVulnerable persona = JSONtoPersonaVulnerable.convertir(node);

            RegistroPersonaVulnerable registro = ContribucionHumanaFactory.registrarPersonaVulnerable(tarjetaPersona, persona, h);
            h.agregarContribucion(registro);
        } else if (posibleHumano.isEmpty()) {
            throw new PermisoDenegadoException("Debes tener una cuenta para realizar esta acción");
        } else {
            throw new TarjetaInexistenteException("La tarjeta ingresada no existe o tiene dueño.");
        }
    }

    public void registrarHeladeraACargo(String json) {
        JsonNode node = ConversorJSON.convertir(json);
        UUID id = UUID.fromString(node.get("id_usuario").asText());
        String heladera = node.get("heladera").asText();

        Optional<Juridica> posibleJuridica = juridicas.buscarPorId(id);
        Optional<Heladera> heladeraOpt = heladeras.buscarPorNombre(heladera);

        if (posibleJuridica.isPresent() && heladeraOpt.isPresent()) {
            Juridica j = posibleJuridica.get();
            Heladera h = heladeraOpt.get();

            HacerseCargoHeladera contribucion = ContribucionJuridicaFactory.hacerseCargoHeladera(h);
            j.agregarContribucion(contribucion);
        } else if (posibleJuridica.isEmpty()) {
            throw new PermisoDenegadoException("Debes tener una cuenta para realizar esta acción");
        } else {
            throw new HeladeraInexistenteException("La heladera ingresada no existe.");
        }

    }
}
