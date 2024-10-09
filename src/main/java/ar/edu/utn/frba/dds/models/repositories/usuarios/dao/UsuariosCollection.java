package ar.edu.utn.frba.dds.models.repositories.usuarios.dao;

import ar.edu.utn.frba.dds.models.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
@AllArgsConstructor
public class UsuariosCollection implements UsuariosDAO{
    private List<Usuario> usuarios;

    @Override
    public void guardar(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    @Override
    public void eliminar(Usuario usuario) {
        this.usuarios.remove(usuario);
    }

    @Override
    public void modificar(Usuario usuario) {
        Optional<Usuario> t1 = this.buscarPorId(usuario.getId());
        t1.ifPresent(t -> {
            usuarios.remove(t);
            usuarios.add(usuario);
        });
    }

    @Override
    public List<Usuario> buscarTodos() {
        return this.usuarios;
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarios.stream().filter(usuario -> usuario.getId().equals(id)).findFirst();
    }

    @Override
    public Optional<Usuario> buscarPorUsername(String username) {
        return this.usuarios
                .stream()
                .filter(usuario -> usuario.getUser().equals(username))
                .findFirst();
    }
}
