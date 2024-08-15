package ar.edu.utn.frba.dds.models.repositories.heladeras;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class HeladerasCollection implements HeladerasDAO {
    private List<Heladera> heladeras;

    @Override
    public void guardar(Heladera heladera) {
        heladeras.add(heladera);
    }

    @Override
    public List<Heladera> buscarTodos() {
        return heladeras;
    }

    @Override
    public void eliminar(Heladera heladera) {
        heladeras.remove(heladera);
    }

    @Override
    public Optional<Heladera> buscarPorNombre(String name) {
        return heladeras
                .stream()
                .filter(h -> h.nombrePunto().equalsIgnoreCase(name))
                .findFirst();
    }
}
