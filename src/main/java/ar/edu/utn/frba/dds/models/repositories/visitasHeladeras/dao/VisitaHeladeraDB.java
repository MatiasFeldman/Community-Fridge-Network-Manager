package ar.edu.utn.frba.dds.models.repositories.visitasHeladeras.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.OfrecerProductoOServicio;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.models.entities.tecnicos.VisitaAHeladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class VisitaHeladeraDB implements VisitaHeladeraDAO, WithSimplePersistenceUnit {

    @Override
    public void guardar(VisitaAHeladera visita) {
        visita.setPresente(true);
        beginTransaction();
        entityManager().persist(visita);
        commitTransaction();
    }

    @Override
    public List<VisitaAHeladera> buscarTodas() {
        List<VisitaAHeladera> visitas = entityManager()
                .createQuery("SELECT c FROM VisitaAHeladera c WHERE c.presente = true", VisitaAHeladera.class)
                .getResultList();

        visitas.forEach(v -> entityManager().refresh(v)); // Forzar sincronización de todas las entidades
        return visitas;
    }

    @Override
    public Optional<VisitaAHeladera> buscarPorId(Long id) {
        VisitaAHeladera visita = entityManager().find(VisitaAHeladera.class, id);
        if (visita != null) {
            entityManager().refresh(visita); // Forzar sincronización de la entidad
        }
        return Optional.ofNullable(visita);
    }

    @Override
    public void actualizar(VisitaAHeladera visita) {
        beginTransaction();
        entityManager().merge(visita);
        commitTransaction();
    }

    @Override
    public void eliminar(VisitaAHeladera visita) {
        visita.setPresente(false);
        this.actualizar(visita);
    }

    @Override
    public List<VisitaAHeladera> buscarPorHeladera(Heladera heladera) {
        List<VisitaAHeladera> visitas = entityManager()
                .createQuery("SELECT c FROM VisitaAHeladera c WHERE c.incidenteAResolver.heladera = :heladera", VisitaAHeladera.class)
                .setParameter("heladera", heladera)
                .getResultList();

        visitas.forEach(v -> entityManager().refresh(v)); // Forzar sincronización de todas las entidades
        return visitas;
    }

    @Override
    public List<VisitaAHeladera> buscarPorTecnico(Tecnico tecnico) {
        List<VisitaAHeladera> visitas = entityManager()
                .createQuery("SELECT c FROM VisitaAHeladera c WHERE c.tecnico = :tecnico", VisitaAHeladera.class)
                .setParameter("tecnico", tecnico)
                .getResultList();

        visitas.forEach(v -> entityManager().refresh(v)); // Forzar sincronización de todas las entidades
        return visitas;
    }
}

