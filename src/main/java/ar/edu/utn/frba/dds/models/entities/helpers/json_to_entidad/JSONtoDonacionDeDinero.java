package ar.edu.utn.frba.dds.models.entities.helpers.json_to_entidad;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.ContribucionHumanaFactory;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.DonacionDeDinero;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.temporal.ChronoUnit;

public class JSONtoDonacionDeDinero {

    public static DonacionDeDinero convertir(JsonNode node){
        double monto = node.get("monto").asDouble();
        boolean esPeriodica = node.get("esPeriodica").asBoolean();
        if (esPeriodica){
            ChronoUnit unidadFrecuencia = ChronoUnit.valueOf(node.get("unidad").asText());
            Integer frecuencia = node.get("frecuencia").asInt();
            return ContribucionHumanaFactory.crearDonacionDeDineroPeriodica(monto, unidadFrecuencia, frecuencia);
        } else{
            return ContribucionHumanaFactory.crearDonacionDeDinero(monto);
        }
    }
}
