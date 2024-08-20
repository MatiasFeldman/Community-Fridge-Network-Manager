package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.exceptions.HeladeraInexistenteException;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.*;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
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
    private HumanosRepository humanos;
    private HeladerasRepository heladeras;
    private TarjetasRepository tarjetas;


    @SneakyThrows
    public void crearDonacionDeViandas(String json) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        UUID id = UUID.fromString(node.get("id_usuario").asText());
        Optional<Humano> humano = humanos.buscarPorUUID(id);

        if (humano.isPresent()) {
            Humano h = humano.get();
            String heladera_destino = node.get("heladera_destino").asText();
            Optional<Heladera> destino = heladeras.buscarPorNombre(heladera_destino);

            if (destino.isPresent()){
                DonacionDeVianda donacion = ContribucionHumanaFactory.crearDonacionDeVianda(id, destino.get());
                h.agregarContribucionPendiente(donacion);
                return;
            }
            throw new HeladeraInexistenteException("No se encontró la heladera");
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
