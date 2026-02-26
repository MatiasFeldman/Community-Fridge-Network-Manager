package ar.edu.utn.frba.dds.models.repositories.permisos.dao;

import ar.edu.utn.frba.dds.models.entities.usuarios.Permiso;

import java.util.List;
import java.util.Optional;

public interface PermisosDAO {
    public void guardar(Permiso permiso);

    public Optional<Permiso> buscarPorNombre(String nombre);

    public List<Permiso> buscarTodos();

    public boolean existePermiso(Long id);

    public void eliminar(Permiso permiso);
}
