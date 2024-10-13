package ar.edu.utn.frba.dds.models.repositories.ofrecerProducto;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.OfrecerProductoOServicio;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import ar.edu.utn.frba.dds.models.repositories.ofrecerProducto.dao.OfrecerProductoDAO;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
@AllArgsConstructor
public class OfrecerProductoRepository {
    private OfrecerProductoDAO dao;

    public void guardar(OfrecerProductoOServicio ofrecerProductoOServicio) {
        this.dao.guardar(ofrecerProductoOServicio);
    }


    public List<OfrecerProductoOServicio> buscarTodas() {
        return dao.buscarTodas();
    }


    public Optional<OfrecerProductoOServicio> buscarPorId(Long id) {
        return dao.buscarPorId(id);
    }

    public void actualizar(OfrecerProductoOServicio ofrecerProductoOServicio) {
        this.dao.actualizar(ofrecerProductoOServicio);
    }


    public void eliminar(OfrecerProductoOServicio ofrecerProductoOServicio) {
        this.dao.eliminar(ofrecerProductoOServicio);
    }

    public Integer cantProductosOfrecidosPor(Juridica colaborador) {
        return dao.cantProductosOfrecidosPor(colaborador);
    }
}
