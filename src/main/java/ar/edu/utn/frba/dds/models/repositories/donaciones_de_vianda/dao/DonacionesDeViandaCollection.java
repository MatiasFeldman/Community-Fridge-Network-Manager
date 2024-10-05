package ar.edu.utn.frba.dds.models.repositories.donaciones_de_vianda.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.DonacionDeVianda;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
public class DonacionesDeViandaCollection implements DonacionesDeViandaDAO{
    private List<DonacionDeVianda> donaciones;

    @Override
    public void guardar(DonacionDeVianda donacionDeVianda) {
        this.donaciones.add(donacionDeVianda);
    }

    @Override
    public List<DonacionDeVianda> buscarTodas() {
        return this.donaciones;
    }

    @Override
    public Optional<DonacionDeVianda> buscarPorId(Long id) {
        return donaciones
                .stream()
                .filter(d -> Objects.equals(d.getId(), id))
                .findFirst();
    }

    @Override
    public void actualizar(DonacionDeVianda donacionDeVianda) {
        this.donaciones.removeIf(d -> Objects.equals(d.getId(), donacionDeVianda.getId()));
        this.donaciones.add(donacionDeVianda);
    }

    @Override
    public void eliminar(DonacionDeVianda donacionDeVianda) {
        this.donaciones.remove(donacionDeVianda);
    }

    @Override
    public List<DonacionDeVianda> buscarPorColaborador(Long id) {
        return donaciones
                .stream()
                .filter(d -> Objects.equals(d.getColaboradorId(), id))
                .toList();
    }

    @Override
    public Integer cantViandasDonadasPor(ColaboradorHumano colaborador) {
        return (int) donaciones
                .stream()
                .filter(d -> Objects.equals(d.getColaborador().getIdUsuario(), colaborador.getIdUsuario()))
                .count();
    }
}
