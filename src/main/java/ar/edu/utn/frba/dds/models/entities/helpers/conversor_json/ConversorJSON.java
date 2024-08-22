package ar.edu.utn.frba.dds.models.entities.helpers.conversor_json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConversorJSON {
    public static JsonNode convertir(String json){
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(json, JsonNode.class);
    }
}
