package ar.edu.utn.frba.dds.models.repositories.juridicas.dao;

import ar.edu.utn.frba.dds.models.entities.personas.Juridica;

import java.util.Optional;
import java.util.UUID;

public interface JuridicasDAO {

    public Optional<Juridica> buscarPorId(Long id);
}
