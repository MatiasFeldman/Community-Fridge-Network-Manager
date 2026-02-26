package ar.edu.utn.frba.dds.models.repositories.servicios.dao;

import ar.edu.utn.frba.dds.models.entities.tecnicos.VisitaAHeladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class ServiciosAHeladeraDataBase implements VisitasDAO, WithSimplePersistenceUnit {
    @Override
    public void guardar(VisitaAHeladera visita) {
        beginTransaction();
        entityManager().persist(visita);
        commitTransaction();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VisitaAHeladera> buscarTodos() {
        return entityManager()
                .createQuery("select v from VisitaAHeladera v where v.presente = true ", VisitaAHeladera.class)
                .getResultList();
    }

    @Override
    public void eliminar(VisitaAHeladera visita) {
        visita.setPresente(false);
        this.modificar(visita);
    }

    @Override
    public Optional<VisitaAHeladera> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(VisitaAHeladera.class, id));
    }

    @Override
    public void modificar(VisitaAHeladera visita) {
        withTransaction(() -> {
            entityManager().merge(visita);
        });
    }
}
