package ar.edu.utn.frba.dds.models.repositories.servicios.dao;

import ar.edu.utn.frba.dds.models.entities.tecnicos.VisitaAHeladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class ServiciosAHeladeraDataBase implements VisitasDAO, WithSimplePersistenceUnit {

    @Override
    public void guardar(VisitaAHeladera visita) {
        visita.setPresente(true);
        beginTransaction();
        entityManager().persist(visita);
        commitTransaction();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VisitaAHeladera> buscarTodos() {
        List<VisitaAHeladera> visitas = entityManager()
                .createQuery("select v from VisitaAHeladera v where v.presente = true ", VisitaAHeladera.class)
                .getResultList();

        visitas.forEach(v -> entityManager().refresh(v)); // Forzar sincronización de todas las entidades
        return visitas;
    }

    @Override
    public void eliminar(VisitaAHeladera visita) {
        visita.setPresente(false);
        this.modificar(visita);
    }

    @Override
    public Optional<VisitaAHeladera> buscarPorId(Long id) {
        VisitaAHeladera visita = entityManager().find(VisitaAHeladera.class, id);
        if (visita != null) {
            entityManager().refresh(visita); // Forzar sincronización si la entidad existe
        }
        return Optional.ofNullable(visita);
    }

    @Override
    public void modificar(VisitaAHeladera visita) {
        withTransaction(() -> {
            entityManager().merge(visita);
        });
    }
}

