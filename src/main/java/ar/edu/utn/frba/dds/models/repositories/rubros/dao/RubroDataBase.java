package ar.edu.utn.frba.dds.models.repositories.rubros.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Rubro;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class RubroDataBase implements RubroDAO, WithSimplePersistenceUnit {

    @Override
    public void guardar(Rubro rubro) {
        rubro.setPresente(true);
        beginTransaction();
        entityManager().persist(rubro);
        commitTransaction();
    }

    @Override
    public void eliminar(Rubro rubro) {
        rubro.setPresente(false);
        this.modificar(rubro);
    }

    @Override
    public void modificar(Rubro rubro) {
        withTransaction(() -> {
            entityManager().merge(rubro);
        });
    }

    @Override
    public List<Rubro> buscarTodos() {
        return entityManager()
                .createQuery("SELECT r FROM Rubro r WHERE r.presente = true ", Rubro.class)
                .getResultList();
    }

    @Override
    public Optional<Rubro> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager()
                .createQuery("SELECT r FROM Rubro r WHERE r.id = :idRubro AND r.presente = true", Rubro.class)
                .setParameter("idRubro", id)
                .getSingleResult());
    }
}
