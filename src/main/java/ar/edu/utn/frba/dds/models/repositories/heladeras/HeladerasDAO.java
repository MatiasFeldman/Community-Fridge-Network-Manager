package ar.edu.utn.frba.dds.models.repositories.heladeras;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HeladerasDAO {
    void guardar(Heladera heladera);
    List<Heladera> buscarTodos();
    void eliminar(Heladera heladera);
}