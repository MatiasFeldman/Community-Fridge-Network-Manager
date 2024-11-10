package ar.edu.utn.frba.dds.models.repositories.usuarios;


import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.repositories.usuarios.dao.UsuariosDAO;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UsuariosRepository {
    private UsuariosDAO usuarios;

    public void guardar(Usuario usuario){
        usuarios.guardar(usuario);
    };

    public void eliminar(Usuario usuario){
        usuarios.eliminar(usuario);
    };

    public void modificar(Usuario usuario){
        usuarios.modificar(usuario);
    };

    public List<Usuario> buscarTodos(){
        return usuarios.buscarTodos();
    };

    public Optional<Usuario> buscarPorId(Long id){
        return usuarios.buscarPorId(id);
    };

    public Optional<Usuario> buscarPorUsername(String username){
        return usuarios.buscarPorUsername(username);
    };

    public boolean existeUsername(String username){
        return usuarios.existeUsername(username);
    };
}
