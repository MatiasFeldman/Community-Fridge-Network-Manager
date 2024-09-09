package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Oferta;
import ar.edu.utn.frba.dds.models.entities.helpers.conversor_json.ConversorJSON;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.juridicas.JuridicasRepository;
import ar.edu.utn.frba.dds.models.repositories.ofertas.OfertasRepository;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

public class OfertasController {
    private OfertasRepository ofertas;
    private HumanosRepository humanos;
    private JuridicasRepository juridicas;

    public void canjearOferta(String json){
        JsonNode node = ConversorJSON.convertir(json);
        Long id = Long.parseLong(node.get("id_usuario").asText());
        Long idOferta = node.get("id_oferta").asLong();
        String rol = node.get("rol").asText();

        Optional<Oferta> posibleOferta = ofertas.buscarPorId(idOferta);
        if (posibleOferta.isEmpty()){
            throw new RuntimeException("No se encontro la oferta");
        }
        Oferta oferta = posibleOferta.get();

        if (rol.equals("HUMANO")){
            Optional<Humano> posibleHumano = humanos.buscarPorId(id);
            if (posibleHumano.isEmpty()){
                throw new RuntimeException("No se encontro el humano");
            }

            Humano humano = posibleHumano.get();
            humano.canjearOferta(oferta);
        } else if (rol.equals("JURIDICA")){
            Optional<Juridica> posibleJuridica = juridicas.buscarPorId(id);
            if (posibleJuridica.isEmpty()){
                throw new RuntimeException("No se encontro el humano");
            }

            Juridica juridica = posibleJuridica.get();
            juridica.canjearOferta(oferta);
        }

        oferta.serCanjeada();
    }
}
