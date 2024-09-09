package ar.edu.utn.frba.dds.models.repositories.humanos.dao;

import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class HumanosDataBase implements HumanosDAO, WithSimplePersistenceUnit {

    @Override
    public void guardar(Humano humano) {
        beginTransaction();
        entityManager().persist(humano);
        commitTransaction();
    }

    public void modificar(Humano humano) {
        withTransaction(() -> {
            entityManager().merge(humano);
        });
    }

    @Override
    public void eliminar(Humano humano) {
        beginTransaction();
        entityManager().remove(humano);
        commitTransaction();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Humano> buscarTodos() {
        return entityManager()
                .createQuery("from " + Humano.class.getName())
                .getResultList();
    }
    @Override
    public Optional<Humano> buscarPorId(Long id){
        return Optional.ofNullable(entityManager().find(Humano.class, id));
    }

    @Override
    public boolean existeUsername(String username) {
        Long count = entityManager()
                .createQuery("SELECT COUNT(h) FROM Humano h WHERE h.user.user = :username", Long.class)
                .setParameter("username", username)
                .getSingleResult();

        return count > 0;
    }


    @Override
    public Optional<Humano> buscarPorDocumento(String tipo, String nro) {
        return Optional.empty(); //TODO
    }
}
