package ar.edu.utn.frba.dds.models.repositories.suscripciones.dao;

import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.entities.suscripciones.SuscripcionAHeladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public class SuscripcionDataBase implements SuscripcionDAO, WithSimplePersistenceUnit {

    @Override
    public void guardar(SuscripcionAHeladera suscripcionAHeladera) {
        suscripcionAHeladera.setPresente(true);
        beginTransaction();
        entityManager().persist(suscripcionAHeladera);
        commitTransaction();
    }

    @Override
    public List<SuscripcionAHeladera> buscarTodos() {
        List<SuscripcionAHeladera> suscripciones = entityManager()
                .createQuery("SELECT h FROM SuscripcionAHeladera h WHERE h.presente = true ", SuscripcionAHeladera.class)
                .getResultList();

        suscripciones.forEach(s -> entityManager().refresh(s)); // Forzar sincronización de todas las entidades
        return suscripciones;
    }

    @Override
    public Optional<SuscripcionAHeladera> buscarPorId(Long id) {
        try {
            SuscripcionAHeladera suscripcion = entityManager()
                    .createQuery("SELECT h FROM SuscripcionAHeladera h WHERE h.id = :idSuscrip AND h.presente = true", SuscripcionAHeladera.class)
                    .setParameter("idSuscrip", id)
                    .getSingleResult();

            entityManager().refresh(suscripcion); // Forzar sincronización de la entidad
            return Optional.ofNullable(suscripcion);
        } catch (NoResultException e) {
            return Optional.empty(); // Si no encuentra resultados
        }
    }

    @Override
    public Optional<SuscripcionAHeladera> buscarPorUsuarioIdYHeladeraId(Long idUsuario, Long idHeladera) {
        try {
            SuscripcionAHeladera suscripcion = entityManager()
                    .createQuery("SELECT h FROM SuscripcionAHeladera h WHERE h.observerSuscripcion.id = :idUsuario AND h.heladera.id = :idHeladera AND h.presente = true", SuscripcionAHeladera.class)
                    .setParameter("idUsuario", idUsuario)
                    .setParameter("idHeladera", idHeladera)
                    .getSingleResult();

            entityManager().refresh(suscripcion); // Forzar sincronización de la entidad
            return Optional.ofNullable(suscripcion);
        } catch (NoResultException e) {
            return Optional.empty(); // Si no encuentra resultados
        }
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

