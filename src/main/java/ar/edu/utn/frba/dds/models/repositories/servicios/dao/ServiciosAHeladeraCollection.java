package ar.edu.utn.frba.dds.models.repositories.servicios.dao;

import ar.edu.utn.frba.dds.models.entities.tecnicos.VisitaAHeladera;

import java.util.List;
import java.util.Optional;

public class ServiciosAHeladeraCollection implements VisitasDAO {
    private List<VisitaAHeladera> serviciosRealizados;

    @Override
    public void guardar(VisitaAHeladera visita) {
        serviciosRealizados.add(visita);
    }

    @Override
    public List<VisitaAHeladera> buscarTodos(){
        return serviciosRealizados;
    }

    @Override
    public void eliminar(VisitaAHeladera visita){
        serviciosRealizados.remove(visita);
    }

    @Override
    public void modificar(VisitaAHeladera visita){
        Optional<VisitaAHeladera> visitaOptional = this.buscarPorId(visita.getId());
        visitaOptional.ifPresent(visita1 -> {
            this.serviciosRealizados.remove(visita1);
            this.serviciosRealizados.add(visita);
        });
    }

    @Override
    public Optional<VisitaAHeladera> buscarPorId(Long id){
        return serviciosRealizados
                .stream()
                .filter(visita -> visita.getId().equals(id))
                .findFirst();
    }
}
