package ar.edu.utn.frba.dds.models.repositories.permisos.dao;


import ar.edu.utn.frba.dds.models.entities.usuarios.Permiso;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class PermisosDataBase implements PermisosDAO, WithSimplePersistenceUnit {
    @Override
    public void guardar(Permiso permiso) {
        entityManager().persist(permiso);
    }

    @Override
    public Optional<Permiso> buscarPorNombre(String nombre) {
        return entityManager()
                .createQuery("SELECT p FROM Permiso p WHERE p.nombre = :nombre", Permiso.class)
                .setParameter("nombre", nombre)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Permiso> buscarTodos() {
        return entityManager()
                .createQuery("from " + Permiso.class.getName())
                .getResultList();
    }

    @Override
    public boolean existePermiso(Long id) {
        return entityManager()
                .createQuery("SELECT COUNT(p) FROM Permiso p WHERE p.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult() > 0;
    }

    @Override
    public void eliminar(Permiso permiso) {
        entityManager().remove(permiso);
    }

    public void modificar(Permiso permiso) {
        withTransaction(() -> {
            entityManager().merge(permiso);  //UPDATE
        });
    }
}
