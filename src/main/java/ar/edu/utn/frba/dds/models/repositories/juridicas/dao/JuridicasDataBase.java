package ar.edu.utn.frba.dds.models.repositories.juridicas.dao;

import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.NoResultException;
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
        List<Juridica> juridicas = entityManager()
                .createQuery("SELECT j FROM Juridica j WHERE j.presente = true ", Juridica.class)
                .getResultList();

        juridicas.forEach(j -> entityManager().refresh(j)); // Forzar sincronización de todas las entidades
        return juridicas;
    }

    @Override
    public Optional<Juridica> buscarPorIdUsuario(Long id) {
        try {
            Juridica juridica = entityManager()
                    .createQuery("SELECT j FROM Juridica j WHERE j.user.id = :idUsuario AND j.presente = true", Juridica.class)
                    .setParameter("idUsuario", id)
                    .getSingleResult();

            entityManager().refresh(juridica); // Forzar sincronización de la entidad
            return Optional.ofNullable(juridica);
        } catch (NoResultException e) {
            return Optional.empty(); // Si no encuentra resultados
        }
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

