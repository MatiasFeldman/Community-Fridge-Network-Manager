package ar.edu.utn.frba.dds.models.repositories.heladeras;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class HeladerasDataBase implements HeladerasDAO, WithSimplePersistenceUnit {
    @Override
    public void guardar(Heladera heladera) {
        beginTransaction();
        entityManager().persist(heladera);
        commitTransaction();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Heladera> buscarTodos() {
        return entityManager()
                .createQuery("from " + Heladera.class.getName())
                .getResultList();
    }

    @Override
    public void modificar(Heladera heladera) {
        withTransaction(() -> {
            entityManager().merge(heladera);
        });
    }

    @Override
    public void eliminar(Heladera heladera) {
        beginTransaction();
        entityManager().remove(heladera);
        commitTransaction();
    }

    @Override
    public Optional<Heladera> buscarPorNombre(String name) {
        Heladera h = entityManager()
                .createQuery("SELECT h FROM Heladera h WHERE h.nombre.nombreDePunto = :name", Heladera.class)
                .setParameter("name", name)
                .getSingleResult();

        return Optional.ofNullable(h);
    }

    @Override
    public Optional<Heladera> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(Heladera.class, id));
    }
}
