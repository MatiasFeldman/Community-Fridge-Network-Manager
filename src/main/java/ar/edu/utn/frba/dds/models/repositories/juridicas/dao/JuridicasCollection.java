package ar.edu.utn.frba.dds.models.repositories.juridicas.dao;

import ar.edu.utn.frba.dds.models.entities.personas.Juridica;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JuridicasCollection implements JuridicasDAO{
    private List<Juridica> juridicas;

    public JuridicasCollection(List<Juridica> juridicas) {
        this.juridicas = juridicas;
    }

    @Override
    public void guardar(Juridica juridica) {
        this.juridicas.add(juridica);
    }

    @Override
    public void eliminar(Juridica juridica) {
        this.juridicas.remove(juridica);
    }

    @Override
    public void modificar(Juridica juridica) {
        Optional<Juridica> juridicaOptional = this.buscarPorId(juridica.getId());
        juridicaOptional.ifPresent(juridica1 -> {
            this.juridicas.remove(juridica1);
            this.juridicas.add(juridica);
        });
    }

    @Override
    public List<Juridica> buscarTodos() {
        return this.juridicas;
    }

    @Override
    public Optional<Juridica> buscarPorId(Long id) {
        return juridicas
                .stream()
                .filter(juridica -> juridica.getId().equals(id))
                .findFirst();
    }

    @Override
    public Boolean existeUsername(String username) {
        return this.juridicas
                .stream()
                .anyMatch(juridica -> juridica.getUser().getUser().equals(username));
    }
}
