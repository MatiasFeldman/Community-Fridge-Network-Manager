package ar.edu.utn.frba.dds.models.repositories.rubros.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Rubro;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;

import java.util.List;
import java.util.Optional;

public interface RubroDAO {
    public void guardar(Rubro rubro);

    public void eliminar(Rubro rubro);

    void modificar(Rubro rubro);

    public List<Rubro> buscarTodos();

    public Optional<Rubro> buscarPorId(Long id);

}
