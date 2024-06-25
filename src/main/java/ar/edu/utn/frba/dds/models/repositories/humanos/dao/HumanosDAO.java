package ar.edu.utn.frba.dds.models.repositories.humanos.dao;

import ar.edu.utn.frba.dds.models.entities.personas.Humano;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HumanosDAO {
    public void guardar(Humano humano);

    public List<Humano> buscarTodos();

    public Optional<Humano> buscarPorId(UUID id);

    public void eliminar(Humano humano);

}
