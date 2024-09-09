package ar.edu.utn.frba.dds.models.repositories.permisos.imp;

import ar.edu.utn.frba.dds.models.entities.usuarios.Permiso;
import ar.edu.utn.frba.dds.models.repositories.permisos.dao.PermisosDAO;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class PermisosRepository {
    private PermisosDAO dao;

    public void guardar(Permiso permiso){
        dao.guardar(permiso);
    }

    public Optional<Permiso> buscarPorNombre(String nombre){
        return dao.buscarPorNombre(nombre);
    }

    public List<Permiso> buscarTodos(){
        return dao.buscarTodos();
    }

    public boolean existePermiso(Long id){
        return dao.existePermiso(id);
    }

    public void eliminar(Permiso permiso){
        dao.eliminar(permiso);
    }
}
