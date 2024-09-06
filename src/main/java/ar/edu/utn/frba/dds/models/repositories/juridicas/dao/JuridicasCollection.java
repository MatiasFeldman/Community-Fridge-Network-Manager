package ar.edu.utn.frba.dds.models.repositories.juridicas.dao;

import ar.edu.utn.frba.dds.models.entities.personas.Juridica;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JuridicasCollection implements JuridicasDAO{
    private List<Juridica> juridicas;

    @Override
    public Optional<Juridica> buscarPorId(Long id) {
        return juridicas
                .stream()
                .filter(juridica -> juridica.getId().equals(id))
                .findFirst();
    }
}
