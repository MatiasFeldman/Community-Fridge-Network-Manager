package ar.edu.utn.frba.dds.models.repositories.juridicas.dao;

import ar.edu.utn.frba.dds.models.entities.personas.Juridica;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JuridicasDAO {

    public void guardar(Juridica juridica);

    public void eliminar(Juridica juridica);

    void modificar(Juridica juridica);

    public List<Juridica> buscarTodos();

    public Optional<Juridica> buscarPorId(Long id);
}
