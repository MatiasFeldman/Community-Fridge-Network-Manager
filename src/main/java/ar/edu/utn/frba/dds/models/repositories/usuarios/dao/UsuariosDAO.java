package ar.edu.utn.frba.dds.models.repositories.usuarios.dao;

import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuariosDAO {
    public void guardar(Usuario usuario);

    public void eliminar(Usuario usuario);

    void modificar(Usuario usuario);

    public List<Usuario> buscarTodos();

    public Optional<Usuario> buscarPorId(Long id);

    public Optional<Usuario> buscarPorUsername(String email);

    public boolean existeUsername(String username);
}
