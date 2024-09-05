package ar.edu.utn.frba.dds.models.entities.helpers.json_to_entidad;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.DenunciaFallaTecnica;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDateTime;


public class JSONtoDenunciaFallaTecnica {

    public static DenunciaFallaTecnica convertir(JsonNode node){
        Long idDenunciante = Long.parseLong(node.get("id_usuario").asText());
        String nombreHeladera = node.get("heladera").asText();
        String descripcion = node.get("descripcion").asText();
        String foto = node.get("foto").asText();
        LocalDateTime fecha = LocalDateTime.parse(node.get("fecha").asText());

        return DenunciaFallaTecnica.of(idDenunciante, descripcion, foto, fecha);


    }
}
