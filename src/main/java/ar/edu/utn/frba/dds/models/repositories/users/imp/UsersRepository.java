package ar.edu.utn.frba.dds.models.repositories.users.imp;

import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.repositories.users.dao.UsersDAO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class UsersRepository {
    private UsersDAO usuarios;

    public UsersRepository(UsersDAO usuarios) {
        this.usuarios = usuarios;
    }


    public void guardar(Usuario usuario) {
        usuarios.guardar(usuario);
    }


    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarios.buscarPorUsername(username);
    }


    public Optional<Usuario> buscarPorId(UUID id) {
        return usuarios.buscarPorId(id);
    }


    public List<Usuario> buscarTodos() {
        return usuarios.buscarTodos();
    }


    public void eliminar(Usuario usuario) {
        usuarios.eliminar(usuario);
    }

    public Boolean existeUsuario(String username) {
        return usuarios.existeUsuario(username);
    }

}
