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
        List<Usuario> usuarios = entityManager()
                .createQuery("SELECT u FROM Usuario u WHERE u.presente = true ", Usuario.class)
                .getResultList();

        usuarios.forEach(u -> entityManager().refresh(u)); // Forzar sincronización de todas las entidades
        return usuarios;
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        Usuario usuario = entityManager().find(Usuario.class, id);
        if (usuario != null) {
            entityManager().refresh(usuario); // Forzar sincronización de la entidad
        }
        return Optional.ofNullable(usuario);
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
                Usuario usuario = usuarios.get(0);
                entityManager().refresh(usuario); // Forzar sincronización de la entidad encontrada
                System.out.println("Usuario encontrado: " + usuario.getUser());
                return Optional.of(usuario);
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

