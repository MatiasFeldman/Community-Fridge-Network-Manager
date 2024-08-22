package ar.edu.utn.frba.dds.models.repositories.juridicas;

import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import ar.edu.utn.frba.dds.models.repositories.juridicas.dao.JuridicasCollection;
import ar.edu.utn.frba.dds.models.repositories.juridicas.dao.JuridicasDAO;

import java.util.Optional;
import java.util.UUID;

public class JuridicasRepository {
    private JuridicasDAO dao;

    public Optional<Juridica> buscarPorId(UUID id) {
        return dao.buscarPorId(id);
    }
}
