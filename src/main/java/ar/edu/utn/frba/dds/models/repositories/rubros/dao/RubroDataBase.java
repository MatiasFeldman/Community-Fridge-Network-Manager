package ar.edu.utn.frba.dds.models.repositories.rubros.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Rubro;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.NoResultException;
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
        List<Rubro> rubros = entityManager()
                .createQuery("SELECT r FROM Rubro r WHERE r.presente = true ", Rubro.class)
                .getResultList();

        rubros.forEach(r -> entityManager().refresh(r)); // Forzar sincronización de todas las entidades
        return rubros;
    }

    @Override
    public Optional<Rubro> buscarPorId(Long id) {
        try {
            Rubro rubro = entityManager()
                    .createQuery("SELECT r FROM Rubro r WHERE r.id = :idRubro AND r.presente = true", Rubro.class)
                    .setParameter("idRubro", id)
                    .getSingleResult();

            entityManager().refresh(rubro); // Forzar sincronización de la entidad
            return Optional.ofNullable(rubro);
        } catch (NoResultException e) {
            return Optional.empty(); // Manejo seguro si no hay resultados
        }
    }
}

