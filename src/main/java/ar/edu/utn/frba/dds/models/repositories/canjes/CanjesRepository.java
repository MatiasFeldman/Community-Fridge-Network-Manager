package ar.edu.utn.frba.dds.models.repositories.canjes;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Canjes;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.OfrecerProductoOServicio;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.repositories.canjes.dao.CanjesDAO;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class CanjesRepository {
    private CanjesDAO dao;

    public void guardar(Canjes canje) {
        this.dao.guardar(canje);
    }


    public List<Canjes> buscarTodas() {
        return dao.buscarTodas();
    }


    public Optional<Canjes> buscarPorId(Long id) {
        return dao.buscarPorId(id);
    }

    public void actualizar(Canjes canje) {
        this.dao.actualizar(canje);
    }


    public void eliminar(Canjes canje) {
        this.dao.eliminar(canje);
    }

    public List<Canjes> buscarPorUsuario(Usuario usuario) {
        return dao.buscarPorUsuario(usuario);
    }
}
