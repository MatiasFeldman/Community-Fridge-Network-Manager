package ar.edu.utn.frba.dds.models.repositories.distribuciones_de_viandas.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.DonacionDeVianda;

import java.util.List;
import java.util.Optional;

public interface DistribucionesDeViandasDAO {
    void guardar(DistribucionViandas donacionDeVianda);
    List<DistribucionViandas> buscarTodas();
    Optional<DistribucionViandas> buscarPorId(Long id);
    void actualizar(DistribucionViandas donacionDeVianda);
    void eliminar(DistribucionViandas donacionDeVianda);
    List<DistribucionViandas> buscarPorColaborador(Long id);
}
