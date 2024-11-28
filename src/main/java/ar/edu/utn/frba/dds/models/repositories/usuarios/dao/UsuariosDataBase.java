package ar.edu.utn.frba.dds.models.repositories.usuarios.dao;

import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class UsuariosDataBase implements UsuariosDAO, WithSimplePersistenceUnit {
    @Override
    public void guardar(Usuario usuario) {
        usuario.setPresente(true);
        beginTransaction();
        entityManager().persist(usuario);
        commitTransaction();
    }

    @Override
    public void eliminar(Usuario usuario) {
        usuario.setPresente(false);
        this.modificar(usuario);
    }

    @Override
    public void modificar(Usuario usuario) {
        withTransaction(() -> {
            entityManager().merge(usuario);
        });
    }

    @Override
    public List<Usuario> buscarTodos() {
        return entityManager()
                .createQuery("SELECT u FROM Usuario u WHERE u.presente = true ", Usuario.class)
                .getResultList();
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(Usuario.class, id));
    }

    @Override
    public Optional<Usuario> buscarPorUsername(String username) {
        try {
            List<Usuario> usuarios = entityManager()
                    .createQuery("SELECT u FROM Usuario u WHERE u.user = :username AND u.presente = true", Usuario.class)
                    .setParameter("username", username)
                    .getResultList();
            if (usuarios.isEmpty()) {
                System.out.println("Usuario no encontrado");
                return Optional.empty();
            } else {
                System.out.println("Usuario encontrado: " + usuarios.get(0).getUser());
                return Optional.of(usuarios.get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al buscar el usuario en la base de datos", e);
        }
    }


    @Override
    public boolean existeUsername(String username) {
        Long count = entityManager()
                .createQuery("SELECT COUNT(u) FROM Usuario u WHERE u.user = :username AND u.presente = true", Long.class)
                .setParameter("username", username)
                .getSingleResult();

        return count > 0;
    }
}
