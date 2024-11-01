package ar.edu.utn.frba.dds.models.repositories.personasVulnerables.dao;

import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import ar.edu.utn.frba.dds.models.entities.personas.PersonaVulnerable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PersonasVulnerablesCollection implements PersonaVulnerableDAO{

    private List<PersonaVulnerable> personaVulnerables;
    private Long currentId = 100L;
    public PersonasVulnerablesCollection(List<PersonaVulnerable> personaVulnerable) {
        this.personaVulnerables = personaVulnerable;
    }

    @Override
    public void guardar(PersonaVulnerable personaVulnerable) {
        personaVulnerable.setId(currentId);
        personaVulnerables.add(personaVulnerable);
        currentId++;
    }

    @Override
    public Optional<PersonaVulnerable> buscarPorId(Long id){
        return personaVulnerables
                .stream()
                .filter(personaVulnerable -> personaVulnerable.getId().equals(id))
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

    @Override
    public void modificar(PersonaVulnerable personaVulnerable){
        Optional<PersonaVulnerable> personaVulnerableOptional = this.buscarPorId(personaVulnerable.getId());
        personaVulnerableOptional.ifPresent(personaVulnerable1 -> {
            this.personaVulnerables.remove(personaVulnerable1);
            this.personaVulnerables.add(personaVulnerable);
        });
    }
}
