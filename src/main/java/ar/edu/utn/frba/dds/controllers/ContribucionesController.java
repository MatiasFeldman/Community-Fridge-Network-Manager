package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.exceptions.HeladeraInexistenteException;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.*;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import ar.edu.utn.frba.dds.models.repositories.contribuciones_humanas.ContribucionesHumanasRepository;
import ar.edu.utn.frba.dds.models.repositories.contribuciones_juridicas.ContribucionesJuridicasRepository;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.tarjetas.TarjetasRepository;
import ar.edu.utn.frba.dds.utils.permisos.PermisoDenegadoException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.util.Optional;
import java.util.UUID;

public class ContribucionesController {
    private ContribucionesHumanasRepository contribucionesHumanas;
    private ContribucionesJuridicasRepository contribucionesJuridicas;
    private HumanosRepository humanos;
    private HeladerasRepository heladeras;
    private TarjetasRepository tarjetas;

    public void registrarContribucionHumana(Humano contribuidor, ContribucionHumana contribucion) {
        contribucionesHumanas.guardar(contribucion);
        contribuidor.agregarContribucion(contribucion);
    }

    public void registrarContribucionJurdiaca(Juridica contribuidor, ContribucionJuridica contribucion) {
        contribucionesJuridicas.guardar(contribucion);
        contribuidor.agregarContribucion(contribucion);
    }

    @SneakyThrows
    public void crearDonacionDeViandas(String json) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        UUID id = UUID.fromString(node.get("id_usuario").asText());
        Optional<Humano> humano = humanos.buscarPorUUID(id);

        if (humano.isPresent()) {
            Humano h = humano.get();
            DonacionDeVianda donacion = ContribucionHumanaFactory.crearDonacionDeVianda(id);
            h.agregarContribucionPendiente(donacion);
            return;
        }

        throw new PermisoDenegadoException("Debes tener una cuenta para realizar esta acción");
    }

    public void crearDistribucionDeViandas(String json) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.convertValue(json, JsonNode.class);
        UUID id = UUID.fromString(node.get("id_usuario").asText());
        Optional<Humano> posibleHumano = humanos.buscarPorUUID(id);

        if (posibleHumano.isPresent()) {
            Humano h = posibleHumano.get();

            String punto_origen = node.get("heladera_origen").asText();
            String punto_destino = node.get("heladera_destino").asText();
            Integer cantidad = node.get("cantidad").asInt();
            String motivo = node.get("motivo").asText();

            Optional<Heladera> origen = heladeras.buscarPorNombre(punto_origen);
            Optional<Heladera> destino = heladeras.buscarPorNombre(punto_destino);

            if (origen.isPresent() && destino.isPresent()) {
                DistribucionViandas distri = ContribucionHumanaFactory.crearDistribucionDeViandas(origen.get(), destino.get(), cantidad, motivo);
                h.agregarContribucionPendiente(distri);
                return;
            }
            throw new HeladeraInexistenteException("No se encontró alguna de las heladeras");

        }
        throw new PermisoDenegadoException("Debes tener una cuenta para realizar esta acción");
    }


}
