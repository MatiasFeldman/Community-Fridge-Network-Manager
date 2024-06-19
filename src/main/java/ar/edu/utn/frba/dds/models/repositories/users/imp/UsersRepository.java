package ar.edu.utn.frba.dds.models.repositories.users.imp;

import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.repositories.IUsersRepository;
import ar.edu.utn.frba.dds.models.repositories.users.dao.IUsersDAO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class UsersRepository implements IUsersRepository {
    private IUsersDAO usuarios;

    public UsersRepository(IUsersDAO usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public void guardar(Usuario usuario) {
        usuarios.guardar(usuario);
    }

    @Override
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarios.buscarPorUsername(username);
    }

    @Override
    public Optional<Usuario> buscarPorId(UUID id) {
        return usuarios.buscarPorId(id);
    }

    @Override
    public List<Usuario> buscarTodos() {
        return usuarios.buscarTodos();
    }


    @Override
    public void eliminar(Usuario usuario) {
        usuarios.eliminar(usuario);
    }

}
