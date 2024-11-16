package ar.edu.utn.frba.dds.models.repositories.tecnicos.dao;

import ar.edu.utn.frba.dds.models.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;

import java.util.List;
import java.util.Optional;

public interface TecnicosDAO {
    void guardar(Tecnico tecnico);
    List<Tecnico> buscarTodos();
    void eliminar(Tecnico tecnico);
    Optional<Tecnico> buscarPorId(Long id);
    Optional<Tecnico> buscarPorIdUsuario(Long id);
    Optional<Tecnico> buscarMasCercano(Direccion origen);
    void modificar(Tecnico tecnico);
}
