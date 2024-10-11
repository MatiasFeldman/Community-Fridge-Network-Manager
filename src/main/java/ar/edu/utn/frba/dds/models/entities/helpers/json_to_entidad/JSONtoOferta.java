package ar.edu.utn.frba.dds.models.entities.helpers.json_to_entidad;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Oferta;
import com.fasterxml.jackson.databind.JsonNode;

public class JSONtoOferta {

    public static Oferta convertir(JsonNode node) {
        String nombre = node.get("nombre").asText();
        Double puntosNecesarios = node.get("puntosNecesarios").asDouble();
        String rubro = node.get("rubro").asText();
        Integer canjesTotales = node.get("canjesTotales").asInt();
        String image = node.get("image").asText();
        return Oferta.of(nombre, puntosNecesarios, rubro, canjesTotales,image);
    }
}
