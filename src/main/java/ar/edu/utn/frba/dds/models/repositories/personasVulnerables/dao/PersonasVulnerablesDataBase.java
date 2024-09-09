package ar.edu.utn.frba.dds.models.repositories.personasVulnerables.dao;

import ar.edu.utn.frba.dds.models.entities.personas.PersonaVulnerable;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class PersonasVulnerablesDataBase implements PersonaVulnerableDAO, WithSimplePersistenceUnit {
    @Override
    public void guardar(PersonaVulnerable personaVulnerable) {
        withTransaction(() -> {
            entityManager().persist(personaVulnerable);
        });
    }

    @Override
    public List<PersonaVulnerable> buscarTodos() {
        return entityManager()
                .createQuery("from PersonaVulnerable", PersonaVulnerable.class)
                .getResultList();
    }

    @Override
    public void eliminar(PersonaVulnerable personaVulnerable) {
        beginTransaction();
        entityManager().remove(personaVulnerable);
        commitTransaction();
    }

    @Override
    public Optional<PersonaVulnerable> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(PersonaVulnerable.class, id));
    }

    @Override
    public void modificar(PersonaVulnerable personaVulnerable) {
        withTransaction(() -> {
            entityManager().merge(personaVulnerable);
        });
    }
}
