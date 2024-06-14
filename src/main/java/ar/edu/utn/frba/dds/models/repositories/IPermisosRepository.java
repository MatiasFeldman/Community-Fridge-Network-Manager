package ar.edu.utn.frba.dds.models.repositories;

import ar.edu.utn.frba.dds.models.entities.usuarios.Permiso;

import java.util.List;
import java.util.Optional;

public interface IPermisosRepository {
    public void guardar(Permiso permiso);

    public Optional<Permiso> buscarPorNombre(String nombre);

    public List<Permiso> buscarTodos();

    public boolean existePermiso(Permiso permiso);

    public void eliminar(Permiso permiso);
}
