package ar.edu.utn.frba.dds.models.entities.helpers.conversor_json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConversorJSON {
    public static JsonNode convertir(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Utilizar readTree para deserializar la cadena JSON
            return mapper.readTree(json);
        } catch (Exception e) {
            e.printStackTrace(); // Manejo de excepciones para detectar errores de deserialización
            return null; // Retornar null en caso de error
        }
    }
}
