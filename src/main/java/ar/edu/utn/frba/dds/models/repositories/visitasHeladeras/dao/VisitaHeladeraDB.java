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
        return entityManager()
                .createQuery("SELECT c FROM VisitaAHeladera c WHERE c.presente = true", VisitaAHeladera.class)
                .getResultList();
    }

    @Override
    public Optional<VisitaAHeladera> buscarPorId(Long id) {
        return entityManager()
                .find(OfrecerProductoOServicio.class, id) == null ? Optional.empty() : Optional.of(entityManager().find(VisitaAHeladera.class, id));
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
        return entityManager()
                .createQuery("SELECT c FROM VisitaAHeladera c WHERE c.incidenteAResolver.heladera = :heladera", VisitaAHeladera.class)
                .setParameter("heladera", heladera)
                .getResultList();
    }

    @Override
    public List<VisitaAHeladera> buscarPorTecnico(Tecnico tecnico) {
        return entityManager()
                .createQuery("SELECT c FROM VisitaAHeladera c WHERE c.tecnico = :tecnico", VisitaAHeladera.class)
                .setParameter("tecnico", tecnico)
                .getResultList();
    }
}
