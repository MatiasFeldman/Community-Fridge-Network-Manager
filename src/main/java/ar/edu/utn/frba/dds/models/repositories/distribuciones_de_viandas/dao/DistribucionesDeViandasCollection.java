package ar.edu.utn.frba.dds.models.repositories.distribuciones_de_viandas.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.DistribucionViandas;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class DistribucionesDeViandasCollection implements DistribucionesDeViandasDAO{
    private List<DistribucionViandas> distribuciones;
    private Long currentId = 100L;

    @Override
    public void guardar(DistribucionViandas donacionDeVianda) {
        donacionDeVianda.setId(currentId);
        this.distribuciones.add(donacionDeVianda);
        currentId++;
    }

    @Override
    public List<DistribucionViandas> buscarTodas() {
        return this.distribuciones;
    }

    @Override
    public Optional<DistribucionViandas> buscarPorId(Long id) {
        return distribuciones
                .stream()
                .filter(d -> d.getId().equals(id))
                .findFirst();
    }

    @Override
    public void actualizar(DistribucionViandas donacionDeVianda) {
        this.distribuciones.removeIf(d -> d.getId().equals(donacionDeVianda.getId()));
        this.distribuciones.add(donacionDeVianda);
    }

    @Override
    public void eliminar(DistribucionViandas donacionDeVianda) {
        this.distribuciones.remove(donacionDeVianda);
    }

    @Override
    public List<DistribucionViandas> buscarPorColaborador(Long id) {
        return distribuciones
                .stream()
                .filter(d -> d.getColaboradorId().equals(id))
                .toList();
    }
}
