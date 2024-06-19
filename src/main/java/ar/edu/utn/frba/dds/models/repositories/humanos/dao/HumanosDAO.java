package ar.edu.utn.frba.dds.models.repositories.humanos.dao;

import ar.edu.utn.frba.dds.models.entities.personas.Humano;

import java.util.List;
import java.util.Optional;

public interface HumanosDAO {
    public void guardar(Humano humano);

    public List<Humano> buscarTodos();

    public Optional<Humano> buscarPorIdentificacion(String nombre, String apellido);

    public void eliminar(Humano humano);

}
