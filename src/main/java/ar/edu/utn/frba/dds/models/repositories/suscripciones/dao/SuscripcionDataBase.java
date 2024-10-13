package ar.edu.utn.frba.dds.models.repositories.suscripciones.dao;

import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.entities.suscripciones.SuscripcionAHeladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class SuscripcionDataBase implements SuscripcionDAO, WithSimplePersistenceUnit {
    @Override
    public void guardar(SuscripcionAHeladera suscripcionAHeladera) {
        beginTransaction();
        entityManager().persist(suscripcionAHeladera);
        commitTransaction();
    }

    @Override
    public List<SuscripcionAHeladera> buscarTodos() {
        return entityManager()
                .createQuery("SELECT h FROM SuscripcionAHeladera h WHERE h.presente = true ", SuscripcionAHeladera.class)
                .getResultList();
    }

    @Override
    public Optional<SuscripcionAHeladera> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager()
                .createQuery("SELECT h FROM SuscripcionAHeladera h WHERE h.id = :idSuscrip AND h.presente = true", SuscripcionAHeladera.class)
                .setParameter("idSuscrip", id)
                .getSingleResult());
    }

    @Override
    public Optional<SuscripcionAHeladera> buscarPorUsuarioId(Long id) {
        return Optional.ofNullable(entityManager()
                .createQuery("SELECT h FROM SuscripcionAHeladera h WHERE h.observerSuscripcion.id = :idSuscrip AND h.presente = true", SuscripcionAHeladera.class)
                .setParameter("idSuscrip", id)
                .getSingleResult());
    }

    @Override
    public void eliminar(SuscripcionAHeladera suscripcionAHeladera) {
        suscripcionAHeladera.setPresente(false);
        this.modificar(suscripcionAHeladera);
    }

    @Override
    public void modificar(SuscripcionAHeladera suscripcionAHeladera) {
        withTransaction(() -> {
            entityManager().merge(suscripcionAHeladera);
        });
    }
}
