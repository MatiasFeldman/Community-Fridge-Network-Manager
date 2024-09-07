package ar.edu.utn.frba.dds.models.entities.helpers.json_to_entidad;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.DenunciaFallaTecnica;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDateTime;


public class JSONtoDenunciaFallaTecnica {

    public static DenunciaFallaTecnica convertir(JsonNode node, Usuario user){
        String descripcion = node.get("descripcion").asText();
        String foto = node.get("foto").asText();
        LocalDateTime fecha = LocalDateTime.parse(node.get("fecha").asText());

        return DenunciaFallaTecnica.of(user.getId(), descripcion, foto, fecha);


    }
}
