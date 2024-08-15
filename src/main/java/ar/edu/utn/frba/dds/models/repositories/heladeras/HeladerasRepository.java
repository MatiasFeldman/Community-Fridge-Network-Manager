package ar.edu.utn.frba.dds.models.repositories.heladeras;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;

import java.util.List;
import java.util.Optional;

public class HeladerasRepository {
    private HeladerasDAO heladeras;

    public HeladerasRepository(HeladerasDAO heladeras) {
        this.heladeras = heladeras;
    }

    public void guardar(Heladera heladera) {
        heladeras.guardar(heladera);
    }

    public List<Heladera> buscarTodos() {
        return heladeras.buscarTodos();
    }

    public void eliminar(Heladera heladera) {
        heladeras.eliminar(heladera);
    }

    public Optional<Heladera> buscarPorNombre(String name){
        return heladeras.buscarPorNombre(name);
    }
}
