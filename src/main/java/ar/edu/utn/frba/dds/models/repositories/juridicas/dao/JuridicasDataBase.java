package ar.edu.utn.frba.dds.models.repositories.juridicas.dao;

import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;


public class JuridicasDataBase implements JuridicasDAO, WithSimplePersistenceUnit {
    @Override
    public void guardar(Juridica juridica) {
        juridica.setPresente(true);
        beginTransaction();
        entityManager().persist(juridica);
        commitTransaction();
    }

    @Override
    public void eliminar(Juridica juridica) {
        juridica.setPresente(false);
        this.modificar(juridica);
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
                .createQuery("SELECT j FROM Juridica j WHERE j.presente = true ", Juridica.class)
                .getResultList();
    }

    @Override
    public Optional<Juridica> buscarPorIdUsuario(Long id) {
        return Optional.ofNullable(entityManager()
                .createQuery("SELECT j FROM Juridica j WHERE j.user.id = :idUsuario AND j.presente = true", Juridica.class)
                .setParameter("idUsuario", id)
                .getSingleResult());
    }

    @Override
    public Boolean existeUsername(String username) {
        Long count = entityManager()
                .createQuery("SELECT COUNT(h) FROM Juridica h WHERE h.user.user = :username AND h.presente = true", Long.class)
                .setParameter("username", username)
                .getSingleResult();

        return count > 0;
    }
}
