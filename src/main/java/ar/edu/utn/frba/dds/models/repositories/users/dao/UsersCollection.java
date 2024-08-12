package ar.edu.utn.frba.dds.models.repositories.users.dao;

import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UsersCollection implements UsersDAO {
    private List<Usuario> usuarios;

    public UsersCollection(List<Usuario> users) {
        this.usuarios = users;
    }

    @Override
    public void guardar(Usuario usuario) {
        usuarios.add(usuario);
    }

    @Override
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarios
                .stream()
                .filter(usuario -> usuario.getUser().equals(username))
                .findFirst();
    }


    @Override
    public Optional<Usuario> buscarPorId(UUID id) {
        return usuarios.stream().filter(usuario -> usuario.getId().equals(id)).findFirst();
    }

    @Override
    public List<Usuario> buscarTodos() {
        return usuarios;
    }

    @Override
    public void eliminar(Usuario usuario) {
        usuarios.remove(usuario);
    }

    @Override
    public Boolean existeUsuario(String username) {
        return usuarios.stream().anyMatch(usuario -> usuario.getUser().equals(username));
    }
}
