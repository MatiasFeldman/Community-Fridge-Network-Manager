package ar.edu.utn.frba.dds.models.repositories.servicios.dao;

import ar.edu.utn.frba.dds.models.entities.tecnicos.VisitaAHeladera;

import java.util.List;
import java.util.Optional;

public interface VisitasDAO {
    public void guardar(VisitaAHeladera visita);

    public List<VisitaAHeladera> buscarTodos();

    public void eliminar(VisitaAHeladera visita);

    public Optional<VisitaAHeladera> buscarPorId(Long id);

    public void modificar(VisitaAHeladera visita);
}
