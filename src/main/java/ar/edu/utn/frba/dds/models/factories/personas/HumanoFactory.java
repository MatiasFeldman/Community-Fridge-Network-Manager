package ar.edu.utn.frba.dds.models.factories.personas;

import ar.edu.utn.frba.dds.dtos.humanos.HumanoInputDTO;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.repositories.ofertas.imp.OfertasRepository;

public class HumanoFactory {

    public static Humano crear(Object solicitud){
        HumanoInputDTO dto = (HumanoInputDTO) solicitud;

        return Humano.create(dto);
    }

}
