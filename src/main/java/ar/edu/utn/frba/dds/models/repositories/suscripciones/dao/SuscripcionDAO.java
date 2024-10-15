package ar.edu.utn.frba.dds.models.repositories.suscripciones.dao;


import ar.edu.utn.frba.dds.models.entities.suscripciones.SuscripcionAHeladera;

import java.util.List;
import java.util.Optional;

public interface SuscripcionDAO {
    public void guardar(SuscripcionAHeladera suscripcionAHeladera);

    public List<SuscripcionAHeladera> buscarTodos();

    public Optional<SuscripcionAHeladera> buscarPorId(Long id);

    public Optional<SuscripcionAHeladera> buscarPorUsuarioIdYHeladeraId(Long usuarioId, Long heladeraId);

    public void eliminar(SuscripcionAHeladera suscripcionAHeladera);

    void modificar(SuscripcionAHeladera suscripcionAHeladera);

}
