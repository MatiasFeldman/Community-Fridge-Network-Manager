package ar.edu.utn.frba.dds.models.repositories.permisos.dao;

import ar.edu.utn.frba.dds.models.entities.usuarios.Permiso;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PermisosCollection implements PermisosDAO{
    private List<Permiso> permisos;

    public PermisosCollection() {
        permisos = new ArrayList<>();
    }

    @Override
    public void guardar(Permiso permiso) {
        if (!this.existePermiso(permiso.getId())) {
            permisos.add(permiso);
        }
    }

    @Override
    public Optional<Permiso> buscarPorNombre(String nombre) {
        return this.permisos
                .stream()
                .filter(c -> c.getNombre().equals(nombre))
                .findFirst();
    }

    @Override
    public List<Permiso> buscarTodos() {
        return permisos;
    }

    @Override
    public boolean existePermiso(Long id) {
        return permisos
                .stream()
                .anyMatch(permiso -> permiso.getId().equals(id));
    }

    @Override
    public void eliminar(Permiso permiso) {
        permisos.remove(permiso);
    }


}
