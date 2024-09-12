package ar.edu.utn.frba.dds.models.repositories.distribuciones_de_viandas.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.DistribucionViandas;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class DistribucionesDeViandasDataBase implements WithSimplePersistenceUnit, DistribucionesDeViandasDAO {
    @Override
    public void guardar(DistribucionViandas donacionDeVianda) {
        beginTransaction();
        entityManager().persist(donacionDeVianda);
        commitTransaction();
    }

    @Override
    public List<DistribucionViandas> buscarTodas() {
        return entityManager()
                .createQuery("SELECT d FROM DistribucionViandas d WHERE d.activa = true", DistribucionViandas.class)
                .getResultList();
    }

    @Override
    public Optional<DistribucionViandas> buscarPorId(Long id) {
        return entityManager()
                .find(DistribucionViandas.class, id) == null ? Optional.empty() : Optional.of(entityManager().find(DistribucionViandas.class, id));
    }

    @Override
    public void actualizar(DistribucionViandas donacionDeVianda) {
        beginTransaction();
        entityManager().merge(donacionDeVianda);
        commitTransaction();
    }

    @Override
    public void eliminar(DistribucionViandas donacionDeVianda) {
        donacionDeVianda.setActiva(false);
        this.actualizar(donacionDeVianda);
    }

    @Override
    public List<DistribucionViandas> buscarPorColaborador(Long id) {
        return entityManager()
                .createQuery("SELECT d FROM DistribucionViandas d WHERE d.colaborador.id = :id AND d.activa = true", DistribucionViandas.class)
                .setParameter("id", id)
                .getResultList();
    }
}
