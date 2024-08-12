package ar.edu.utn.frba.dds.models.repositories.users.dao;

import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsersDAO {
    public void guardar(Usuario usuario);

    public Optional<Usuario> buscarPorUsername(String username);

    public Optional<Usuario> buscarPorId(UUID id);

    public List<Usuario> buscarTodos();

    public void eliminar(Usuario usuario);

    public Boolean existeUsuario(String username);
}
