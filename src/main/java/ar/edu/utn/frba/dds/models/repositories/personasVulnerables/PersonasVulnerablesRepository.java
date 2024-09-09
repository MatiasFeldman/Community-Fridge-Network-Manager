package ar.edu.utn.frba.dds.models.repositories.personasVulnerables;

import ar.edu.utn.frba.dds.models.entities.personas.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.repositories.personasVulnerables.dao.PersonaVulnerableDAO;

import java.util.List;
import java.util.Optional;

public class PersonasVulnerablesRepository {

    private PersonaVulnerableDAO personaVulnerables;

    public PersonasVulnerablesRepository(PersonaVulnerableDAO personaVulnerables) {
        this.personaVulnerables = personaVulnerables;
    }

    public void guardar(PersonaVulnerable personaVulnerable) {
        personaVulnerables.guardar(personaVulnerable);
    }

    public Optional<PersonaVulnerable> buscarPorUUID(Long id) {
        return personaVulnerables.buscarPorId(id);
    }

    public List<PersonaVulnerable> buscarTodos() {
        return personaVulnerables.buscarTodos();
    }

    public void eliminar(PersonaVulnerable personaVulnerable) {
        personaVulnerables.eliminar(personaVulnerable);
    }

    void modificar(PersonaVulnerable personaVulnerable){personaVulnerables.modificar(personaVulnerable);}
}
