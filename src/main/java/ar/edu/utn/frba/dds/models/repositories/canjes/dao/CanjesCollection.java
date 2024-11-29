package ar.edu.utn.frba.dds.models.repositories.canjes.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Canjes;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.repositories.canjes.CanjesRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
public class CanjesCollection implements CanjesDAO{
    private List<Canjes> canjes;
    private Long currentId = 100L;
    @Override
    public void guardar(Canjes canje) {
        canje.setId(currentId);
        this.canjes.add(canje);
        currentId++;
    }

    public CanjesCollection(List<Canjes> canjes) {
        this.canjes = canjes;
    }

    @Override
    public List<Canjes> buscarTodas() {
        return this.canjes;
    }

    @Override
    public Optional<Canjes> buscarPorId(Long id) {
        return canjes
                .stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findFirst();
    }

    @Override
    public void actualizar(Canjes canje) {
        this.canjes.removeIf(c -> Objects.equals(c.getId(), canje.getId()));
        this.canjes.add(canje);
    }

    @Override
    public void eliminar(Canjes canje) {
        this.canjes.remove(canje);
    }

    @Override
    public List<Canjes> buscarPorUsuario(Usuario usuario) {
        return canjes
                .stream()
                .filter(c -> Objects.equals(c.getUsuario().getId(), usuario.getId()))
                .toList();
    }
}
