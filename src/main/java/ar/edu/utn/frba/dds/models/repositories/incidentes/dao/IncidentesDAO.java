package ar.edu.utn.frba.dds.models.repositories.incidentes.dao;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Incidente;

import java.util.List;
import java.util.Optional;

public interface IncidentesDAO {
    public void guardar(Incidente incidente);

    public List<Incidente> buscarTodos();

    public void eliminar(Incidente incidente);

    public Optional<Incidente> buscarIncidente(Incidente incidente);

    boolean buscarFallaTecnicaEnHeladera(Heladera heladera);
}
