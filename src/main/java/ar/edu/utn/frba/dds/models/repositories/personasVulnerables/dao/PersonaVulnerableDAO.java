package ar.edu.utn.frba.dds.models.repositories.personasVulnerables.dao;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Incidente;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.entities.personas.PersonaVulnerable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonaVulnerableDAO {
    public void guardar(PersonaVulnerable personaVulnerable);

    public List<PersonaVulnerable> buscarTodos();

    public void eliminar(PersonaVulnerable personaVulnerable);

    public Optional<PersonaVulnerable> buscarPorId(String id);
}
