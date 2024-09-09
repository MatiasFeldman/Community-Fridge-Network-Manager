package ar.edu.utn.frba.dds.models.repositories.juridicas.dao;

import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class JuridicasDataBase implements JuridicasDAO, WithSimplePersistenceUnit {
    @Override
    public void guardar(Juridica juridica) {
        beginTransaction();
        entityManager().persist(juridica);
        commitTransaction();
    }

    @Override
    public void eliminar(Juridica juridica) {
        beginTransaction();
        entityManager().remove(juridica);
        commitTransaction();
    }

    @Override
    public void modificar(Juridica juridica) {
        withTransaction(() -> {
            entityManager().merge(juridica);
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Juridica> buscarTodos() {
        return entityManager()
                .createQuery("from " + Juridica.class.getName())
                .getResultList();
    }

    @Override
    public Optional<Juridica> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(Juridica.class, id));
    }
}
