package ar.edu.utn.frba.dds.models.repositories.canjes.dao;


import ar.edu.utn.frba.dds.models.entities.colaboraciones.Canjes;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;

import java.util.List;
import java.util.Optional;

public interface CanjesDAO {
    void guardar(Canjes canje);
    List<Canjes> buscarTodas();
    Optional<Canjes> buscarPorId(Long id);
    void actualizar(Canjes canje);
    void eliminar(Canjes canje);

    List<Canjes> buscarPorUsuario(Usuario usuario);
}
