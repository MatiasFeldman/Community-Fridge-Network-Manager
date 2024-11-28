package ar.edu.utn.frba.dds.models.repositories.donacion_dinero.DAO;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.DonacionDeDinero;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class DonacionDineroDB implements DonacionDineroDAO, WithSimplePersistenceUnit {
    @Override
    public void guardar(DonacionDeDinero donacionDeDinero) {
        donacionDeDinero.setPresente(true);
        beginTransaction();
        entityManager().persist(donacionDeDinero);
        commitTransaction();
    }

    @Override
    public List<DonacionDeDinero> buscarTodas() {
        return entityManager()
                .createQuery("SELECT d FROM DistribucionViandas d WHERE d.presente = true", DonacionDeDinero.class)
                .getResultList();
    }

    @Override
    public Optional<DonacionDeDinero> buscarPorId(Long id) {
        return entityManager()
                .find(DonacionDeDinero.class, id) == null ? Optional.empty() : Optional.of(entityManager().find(DonacionDeDinero.class, id));
    }

    @Override
    public void actualizar(DonacionDeDinero donacionDeDinero) {
        beginTransaction();
        entityManager().merge(donacionDeDinero);
        commitTransaction();
    }

    @Override
    public void eliminar(DonacionDeDinero donacionDeDinero) {
        donacionDeDinero.setPresente(false);
        this.actualizar(donacionDeDinero);
    }

    @Override
    public List<DonacionDeDinero> buscarPorColaborador(Long id) {
        return entityManager()
                .createQuery("SELECT d FROM DistribucionViandas d WHERE d.colaborador.id = :id AND d.presente = true", DonacionDeDinero.class)
                .setParameter("id", id)
                .getResultList();
    }
}
