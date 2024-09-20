package ar.edu.utn.frba.dds.models.repositories.humanos.dao;

import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class HumanosDataBase implements HumanosDAO, WithSimplePersistenceUnit {

    @Override
    public void guardar(ColaboradorHumano colaboradorHumano) {
        beginTransaction();
        entityManager().persist(colaboradorHumano);
        commitTransaction();
    }

    public void modificar(ColaboradorHumano colaboradorHumano) {
        withTransaction(() -> {
            entityManager().merge(colaboradorHumano);
        });
    }

    @Override
    public void eliminar(ColaboradorHumano colaboradorHumano) {
        colaboradorHumano.setPresente(false);
        this.modificar(colaboradorHumano);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ColaboradorHumano> buscarTodos() {
        return entityManager()
                .createQuery("SELECT h FROM ColaboradorHumano h WHERE h.presente = true ", ColaboradorHumano.class)
                .getResultList();
    }
    @Override
    public Optional<ColaboradorHumano> buscarPorId(Long id){
        return Optional.ofNullable(entityManager().find(ColaboradorHumano.class, id));
    }

    @Override
    public boolean existeUsername(String username) {
        Long count = entityManager()
                .createQuery("SELECT COUNT(h) FROM ColaboradorHumano h WHERE h.user.user = :username AND h.presente = true", Long.class)
                .setParameter("username", username)
                .getSingleResult();

        return count > 0;
    }


    @Override
    public Optional<ColaboradorHumano> buscarPorDocumento(String tipo, String nro) {
        return Optional.empty(); //TODO
    }
}
