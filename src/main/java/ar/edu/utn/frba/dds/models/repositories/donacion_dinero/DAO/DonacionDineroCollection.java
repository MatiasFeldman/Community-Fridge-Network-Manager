package ar.edu.utn.frba.dds.models.repositories.donacion_dinero.DAO;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.DonacionDeDinero;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
@AllArgsConstructor
public class DonacionDineroCollection implements DonacionDineroDAO{
    private List<DonacionDeDinero> donaciones;
    private Long currentId = 100L;

    @Override
    public void guardar(DonacionDeDinero donacionDeDinero) {
        donacionDeDinero.setId(currentId);
        this.donaciones.add(donacionDeDinero);
        currentId++;
    }

    @Override
    public List<DonacionDeDinero> buscarTodas() {
        return this.donaciones;
    }

    @Override
    public Optional<DonacionDeDinero> buscarPorId(Long id) {
        return donaciones
                .stream()
                .filter(d -> d.getId().equals(id))
                .findFirst();
    }

    @Override
    public void actualizar(DonacionDeDinero donacionDeDinero) {
        this.donaciones.removeIf(d -> d.getId().equals(donacionDeDinero.getId()));
        this.donaciones.add(donacionDeDinero);
    }

    @Override
    public void eliminar(DonacionDeDinero donacionDeVianda) {
        this.donaciones.remove(donacionDeVianda);
    }

    @Override
    public List<DonacionDeDinero> buscarPorColaborador(Long id) {
        return donaciones
                .stream()
                .filter(d -> d.getColaboradorId().equals(id))
                .toList();
    }
}
