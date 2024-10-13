package ar.edu.utn.frba.dds.models.repositories.ofrecerProducto.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.OfrecerProductoOServicio;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;

import java.util.List;
import java.util.Optional;

public interface OfrecerProductoDAO {
    void guardar(OfrecerProductoOServicio ofrecerProductoOServicio);
    List<OfrecerProductoOServicio> buscarTodas();
    Optional<OfrecerProductoOServicio> buscarPorId(Long id);
    void actualizar(OfrecerProductoOServicio ofrecerProductoOServicio);
    void eliminar(OfrecerProductoOServicio ofrecerProductoOServicio);
    Integer cantProductosOfrecidosPor(Juridica colaborador);
}
