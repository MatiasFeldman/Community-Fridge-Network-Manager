package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dtos.tecnicos.TecnicoDTO;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.services.tecnicos.TecnicosService;

public class TecnicosController {
    private TecnicosService tecnicosService;

    public TecnicosController(TecnicosService tecnicosService){
        this.tecnicosService = tecnicosService;
    }

    public Object crear(Object solicitud){
        TecnicoDTO dto = (TecnicoDTO) solicitud;

        Usuario actual = null; //TODO: obtener usuario actual

        return this.tecnicosService.crearTecnico(dto, actual);
    }
}
