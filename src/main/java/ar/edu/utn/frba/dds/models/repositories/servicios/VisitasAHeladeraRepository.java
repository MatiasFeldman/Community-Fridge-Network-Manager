package ar.edu.utn.frba.dds.models.repositories.servicios;

import ar.edu.utn.frba.dds.models.entities.tecnicos.VisitaAHeladera;
import ar.edu.utn.frba.dds.models.repositories.servicios.dao.VisitasDAO;

import java.util.List;
import java.util.Optional;

public class VisitasAHeladeraRepository {
    private VisitasDAO serviciosRealizados;

    public void guardar(VisitaAHeladera visita){
        serviciosRealizados.guardar(visita);
    }

    public List<VisitaAHeladera> buscarTodos(){return serviciosRealizados.buscarTodos();}

    public void eliminar(VisitaAHeladera visita){serviciosRealizados.eliminar(visita);}

    public Optional<VisitaAHeladera> buscarPorId(Long id){return serviciosRealizados.buscarPorId(id);}

    public void modificar(VisitaAHeladera visita){serviciosRealizados.modificar(visita);}
}
