package ar.edu.utn.frba.dds.models.factories.personas;

import ar.edu.utn.frba.dds.dtos.humanos.HumanoInputDTO;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;

public class HumanoFactory {

    public static ColaboradorHumano crear(Object solicitud){
        HumanoInputDTO dto = (HumanoInputDTO) solicitud;

        return ColaboradorHumano.create(dto);
    }

}
