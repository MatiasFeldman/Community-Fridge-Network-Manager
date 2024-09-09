package ar.edu.utn.frba.dds.models.repositories.incidentes.imp;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Incidente;
import ar.edu.utn.frba.dds.models.repositories.incidentes.dao.IncidentesDAO;

import java.util.List;
import java.util.Optional;

public class IncidentesRepository {
    private IncidentesDAO incidentes;

    public void guardar(Incidente incidente) {
        this.incidentes.guardar(incidente);
    }

    public List<Incidente> buscarTodos() {
        return this.incidentes.buscarTodos();
    }

    public void eliminar(Incidente incidente) {
        this.incidentes.eliminar(incidente);
    }

    public Optional<Incidente> buscarIncidente(Long id){
        return this.incidentes.buscarIncidente(id);
    }

    public boolean buscarFallaTecnicaEnHeladera(Heladera heladera) {
        return this.incidentes.buscarFallaTecnicaEnHeladera(heladera);
    }

    public void modificar(Incidente incidente){
        this.incidentes.modificar(incidente);
    }
}
