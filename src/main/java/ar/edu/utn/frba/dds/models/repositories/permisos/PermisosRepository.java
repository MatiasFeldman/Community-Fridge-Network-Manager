package ar.edu.utn.frba.dds.models.repositories.permisos;

import ar.edu.utn.frba.dds.models.entities.usuarios.Permiso;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PermisosRepository implements ar.edu.utn.frba.dds.models.repositories.PermisosRepository {
    private List<Permiso> permisos;

    public PermisosRepository() {
        permisos = new ArrayList<>();
    }

    @Override
    public void guardar(Permiso permiso) {
        if (!this.existePermiso(permiso)) {
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
    public boolean existePermiso(Permiso permiso) {
        return permisos.contains(permiso);
    }

    @Override
    public void eliminar(Permiso permiso) {
        permisos.remove(permiso);
    }


}
