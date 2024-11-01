package ar.edu.utn.frba.dds.models.repositories.ofrecerProducto.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.DonacionDeVianda;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.OfrecerProductoOServicio;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@AllArgsConstructor
public class OfrecerProductoCollection implements OfrecerProductoDAO {
    private List<OfrecerProductoOServicio> colaboraciones;
    private Long currentId = 100L;
    @Override
    public void guardar(OfrecerProductoOServicio ofrecerProductoOServicio) {
        ofrecerProductoOServicio.setId(currentId);
        this.colaboraciones.add(ofrecerProductoOServicio);
        currentId++;
    }

    @Override
    public List<OfrecerProductoOServicio> buscarTodas() {
        return this.colaboraciones;
    }

    @Override
    public Optional<OfrecerProductoOServicio> buscarPorId(Long id) {
        return colaboraciones
                .stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findFirst();
    }

    @Override
    public void actualizar(OfrecerProductoOServicio ofrecerProductoOServicio) {
        this.colaboraciones.removeIf(c -> Objects.equals(c.getId(), ofrecerProductoOServicio.getId()));
        this.colaboraciones.add(ofrecerProductoOServicio);
    }

    @Override
    public void eliminar(OfrecerProductoOServicio ofrecerProductoOServicio) {
        this.colaboraciones.remove(ofrecerProductoOServicio);
    }
    @Override
    public Integer cantProductosOfrecidosPor(Juridica colaborador) {
        return (int) colaboraciones
                .stream()
                .filter(d -> Objects.equals(d.getJuridica().getId(), colaborador.getId()))
                .count();
    }
}
