package ar.edu.utn.frba.dds.models.repositories.personasVulnerables.dao;

import ar.edu.utn.frba.dds.models.entities.personas.PersonaVulnerable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PersonasVulnerablesCollection implements PersonaVulnerableDAO{

    private List<PersonaVulnerable> personaVulnerables;
    public PersonasVulnerablesCollection(List<PersonaVulnerable> personaVulnerable) {
        this.personaVulnerables = personaVulnerable;
    }

    @Override
    public void guardar(PersonaVulnerable personaVulnerable) {
        personaVulnerables.add(personaVulnerable);
    }

    @Override
    public Optional<PersonaVulnerable> buscarPorId(String id){
        return personaVulnerables
                .stream()
                .filter(personaVulnerable -> personaVulnerable.getIdPersonaVulnerable().equals(id))
                .findFirst();
    }

    @Override
    public List<PersonaVulnerable> buscarTodos() {
        return personaVulnerables;
    }

    @Override
    public void eliminar(PersonaVulnerable personaVulnerable) {
        personaVulnerables.remove(personaVulnerable);
    }
}
