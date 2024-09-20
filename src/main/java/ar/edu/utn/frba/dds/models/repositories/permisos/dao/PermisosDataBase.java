package ar.edu.utn.frba.dds.models.repositories.permisos.dao;


import ar.edu.utn.frba.dds.models.entities.usuarios.Permiso;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class PermisosDataBase implements PermisosDAO, WithSimplePersistenceUnit {
    @Override
    public void guardar(Permiso permiso) {
        beginTransaction();
        entityManager().persist(permiso);
        commitTransaction();
    }

    @Override
    public Optional<Permiso> buscarPorNombre(String nombre) {
        return entityManager()
                .createQuery("SELECT p FROM Permiso p WHERE p.nombre = :nombre and p.presente = true", Permiso.class)
                .setParameter("nombre", nombre)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Permiso> buscarTodos() {
        return entityManager()
                .createQuery("select p from Permiso p where p.presente = true ", Permiso.class)
                .getResultList();
    }

    @Override
    public boolean existePermiso(Long id) {
        return entityManager()
                .createQuery("SELECT COUNT(p) FROM Permiso p WHERE p.id = :id and p.presente = true", Long.class)
                .setParameter("id", id)
                .getSingleResult() > 0;
    }

    @Override
    public void eliminar(Permiso permiso) {
        permiso.setPresente(false);
        this.modificar(permiso);
    }

    public void modificar(Permiso permiso) {
        withTransaction(() -> {
            entityManager().merge(permiso);  //UPDATE
        });
    }
}
