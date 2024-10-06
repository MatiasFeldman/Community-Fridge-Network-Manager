package ar.edu.utn.frba.dds.models.repositories.usuarios;


import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.repositories.usuarios.dao.UsuariosDAO;

import java.util.List;
import java.util.Optional;

public class UsuariosRepository {
    private UsuariosDAO usuarios;

    public void guardar(Usuario usuario){
        usuarios.guardar(usuario);
    };

    public void eliminar(Usuario usuario){
        usuarios.eliminar(usuario);
    };

    void modificar(Usuario usuario){
        usuarios.modificar(usuario);
    };

    public List<Usuario> buscarTodos(){
        return usuarios.buscarTodos();
    };

    public Optional<Usuario> buscarPorId(Long id){
        return usuarios.buscarPorId(id);
    };

    public Optional<Usuario> buscarPorEmail(String email){
        return usuarios.buscarPorEmail(email);
    };
}
