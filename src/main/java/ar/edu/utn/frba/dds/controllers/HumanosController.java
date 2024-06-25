package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dtos.humanos.HumanoInputDTO;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;

public class HumanosController {
    public Object crear(Object solicitud){
        HumanoInputDTO dto = (HumanoInputDTO) solicitud;

        return Humano.create(dto);
    }
}
