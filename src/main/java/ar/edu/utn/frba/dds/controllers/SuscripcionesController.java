package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.exceptions.HeladeraInexistenteException;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.helpers.conversor_json.ConversorJSON;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import ar.edu.utn.frba.dds.models.factories.Suscripciones.SuscripcionesFactory;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.juridicas.JuridicasRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;

import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
public class SuscripcionesController {
    private HeladerasRepository heladeras;
    private HumanosRepository humanos;
    private JuridicasRepository juridicas;

    public void suscribirseAHeladera(String json){
        JsonNode node = ConversorJSON.convertir(json);
        Long id_usuario = Long.parseLong(node.get("id_usuario").asText());
        String tipo_colaborador = node.get("tipo_colaborador").asText();
        String punto_heladera = node.get("heladera").asText();
        String medioDeContacto = node.get("medio_de_contacto").asText();

        Optional<Heladera> heladera = heladeras.buscarPorNombre(punto_heladera);

        if (heladera.isEmpty()){
            throw new HeladeraInexistenteException("No se encontro la heladera");
        } else{
            Heladera h = heladera.get();
            JsonNode suscripcionesNode = node.get("suscripciones");
            if (Objects.equals(tipo_colaborador, "HUMANO")){
                Optional<ColaboradorHumano> posibleHumano = humanos.buscarPorId(id_usuario);
                if (posibleHumano.isPresent()){
                    ColaboradorHumano colaboradorHumano = posibleHumano.get();
                    for (JsonNode suscripcion : suscripcionesNode){
                        SuscripcionesFactory.crearSuscripcion(colaboradorHumano, h, suscripcion, medioDeContacto);
                    }
                }
            } else if(Objects.equals(tipo_colaborador, "JURIDICA")){
                Optional<Juridica> posibleJuridica = juridicas.buscarPorId(id_usuario);
                if (posibleJuridica.isPresent()){
                    Juridica juridica = posibleJuridica.get();
                    for (JsonNode suscripcion : suscripcionesNode){
                        SuscripcionesFactory.crearSuscripcion(juridica, h, suscripcion, medioDeContacto);
                    }
                }
            }
        }



    }
}
