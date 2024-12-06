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
        donacionDeVianda.setPresente(true);
        beginTransaction();
        entityManager().persist(donacionDeVianda);
        commitTransaction();
    }

    @Override
    public List<DistribucionViandas> buscarTodas() {
        List<DistribucionViandas> distribuciones = entityManager()
                .createQuery("SELECT d FROM DistribucionViandas d WHERE d.presente = true", DistribucionViandas.class)
                .getResultList();

        distribuciones.forEach(d -> entityManager().refresh(d)); // Forzar sincronización de todas las entidades
        return distribuciones;
    }

    @Override
    public Optional<DistribucionViandas> buscarPorId(Long id) {
        DistribucionViandas distribucion = entityManager().find(DistribucionViandas.class, id);
        if (distribucion != null) {
            entityManager().refresh(distribucion); // Forzar sincronización de la entidad
        }
        return Optional.ofNullable(distribucion);
    }

    @Override
    public void actualizar(DistribucionViandas donacionDeVianda) {
        beginTransaction();
        entityManager().merge(donacionDeVianda);
        commitTransaction();
    }

    @Override
    public void eliminar(DistribucionViandas donacionDeVianda) {
        donacionDeVianda.setPresente(false);
        this.actualizar(donacionDeVianda);
    }

    @Override
    public List<DistribucionViandas> buscarPorColaborador(Long id) {
        List<DistribucionViandas> distribuciones = entityManager()
                .createQuery("SELECT d FROM DistribucionViandas d WHERE d.colaborador.id = :id AND d.presente = true", DistribucionViandas.class)
                .setParameter("id", id)
                .getResultList();

        distribuciones.forEach(d -> entityManager().refresh(d)); // Forzar sincronización de todas las entidades
        return distribuciones;
    }
}
