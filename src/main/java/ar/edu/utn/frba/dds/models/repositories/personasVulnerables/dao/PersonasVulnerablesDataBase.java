package ar.edu.utn.frba.dds.models.repositories.personasVulnerables.dao;

import ar.edu.utn.frba.dds.models.entities.personas.PersonaVulnerable;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class PersonasVulnerablesDataBase implements PersonaVulnerableDAO, WithSimplePersistenceUnit {

    @Override
    public void guardar(PersonaVulnerable personaVulnerable) {
        personaVulnerable.setPresente(true);
        withTransaction(() -> {
            entityManager().persist(personaVulnerable);
        });
    }

    @Override
    public List<PersonaVulnerable> buscarTodos() {
        List<PersonaVulnerable> personas = entityManager()
                .createQuery("select p from PersonaVulnerable p where p.presente = true", PersonaVulnerable.class)
                .getResultList();

        personas.forEach(p -> entityManager().refresh(p)); // Forzar sincronización de todas las entidades
        return personas;
    }

    @Override
    public void eliminar(PersonaVulnerable personaVulnerable) {
        personaVulnerable.setPresente(false);
        this.modificar(personaVulnerable);
    }

    @Override
    public Optional<PersonaVulnerable> buscarPorId(Long id) {
        PersonaVulnerable persona = entityManager().find(PersonaVulnerable.class, id);
        if (persona != null) {
            entityManager().refresh(persona); // Forzar sincronización si la entidad existe
        }
        return Optional.ofNullable(persona);
    }

    @Override
    public void modificar(PersonaVulnerable personaVulnerable) {
        withTransaction(() -> {
            entityManager().merge(personaVulnerable);
        });
    }
}

