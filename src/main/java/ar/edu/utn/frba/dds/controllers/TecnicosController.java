package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dtos.tecnicos.TecnicoDTO;
import ar.edu.utn.frba.dds.models.entities.tecnicos.VisitaAHeladera;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.repositories.servicios.VisitasAHeladeraRepository;
import ar.edu.utn.frba.dds.services.tecnicos.TecnicosService;

public class TecnicosController {
    private TecnicosService tecnicosService;
    private VisitasAHeladeraRepository visitasAHeladeraRepository;

    public TecnicosController(TecnicosService tecnicosService, VisitasAHeladeraRepository visitasAHeladeraRepository) {
        this.tecnicosService = tecnicosService;
        this.visitasAHeladeraRepository = visitasAHeladeraRepository;
    }

    public Object crear(Object solicitud) {
        TecnicoDTO dto = (TecnicoDTO) solicitud;

        Usuario actual = null; //TODO: obtener usuario actual

        return this.tecnicosService.crearTecnico(dto, actual);
    }

    public void registrarVisitaAHeladera(VisitaAHeladera visita) {
        visitasAHeladeraRepository.guardar(visita);
        if (visita.isSolucionado()) {
            visita.getHeladeraFallada().activar();
        }

    }
}
