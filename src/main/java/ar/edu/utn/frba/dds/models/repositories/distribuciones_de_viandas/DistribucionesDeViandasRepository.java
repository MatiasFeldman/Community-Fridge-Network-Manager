package ar.edu.utn.frba.dds.models.repositories.distribuciones_de_viandas;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.DistribucionViandas;
import ar.edu.utn.frba.dds.models.repositories.distribuciones_de_viandas.dao.DistribucionesDeViandasDAO;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class DistribucionesDeViandasRepository {
    private DistribucionesDeViandasDAO dao;


    public void guardar(DistribucionViandas donacionDeVianda) {
        this.dao.guardar(donacionDeVianda);
    }


    public List<DistribucionViandas> buscarTodas() {
        return this.dao.buscarTodas();
    }


    public Optional<DistribucionViandas> buscarPorId(Long id) {
        return this.dao.buscarPorId(id);
    }


    public void actualizar(DistribucionViandas donacionDeVianda) {
        this.dao.actualizar(donacionDeVianda);
    }


    public void eliminar(DistribucionViandas donacionDeVianda) {
        this.dao.eliminar(donacionDeVianda);
    }


    public List<DistribucionViandas> buscarPorColaborador(Long id) {
        return this.dao.buscarPorColaborador(id);
    }
}
