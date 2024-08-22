package ar.edu.utn.frba.dds.models.entities.helpers.json_to_entidad;

import ar.edu.utn.frba.dds.models.entities.personas.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDate;

public class JSONtoPersonaVulnerable {

    public static PersonaVulnerable convertir(JsonNode node){
        JsonNode personaVulnerableData = node.get("persona_vulnerable");
        String nombre = personaVulnerableData.get("nombre").asText();
        LocalDate nacimiento = LocalDate.parse(personaVulnerableData.get("fecha_nacimiento").asText());
        String nroDocumento = personaVulnerableData.get("nro_documento").asText();
        Integer menoresACargo = personaVulnerableData.get("menores_a_cargo").asInt();
        JsonNode domicilioData = personaVulnerableData.get("domicilio");
        String calle = domicilioData.get("calle").asText();
        int altura = domicilioData.get("altura").asInt();
        return PersonaVulnerable.of(nombre, nacimiento, Direccion.of(calle, altura), nroDocumento, menoresACargo);
    }
}
