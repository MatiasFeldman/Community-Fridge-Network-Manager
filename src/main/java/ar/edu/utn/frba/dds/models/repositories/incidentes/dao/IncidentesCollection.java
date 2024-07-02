package ar.edu.utn.frba.dds.models.repositories.incidentes.dao;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Incidente;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.TipoEvento;

import java.util.List;
import java.util.Optional;

public class IncidentesCollection implements IncidentesDAO{
    private List<Incidente> incidentes;

    public IncidentesCollection(List<Incidente> incidentes) {
        this.incidentes = incidentes;
    }

    @Override
    public void guardar(Incidente incidente) {
        incidentes.add(incidente);
    }

    @Override
    public List<Incidente> buscarTodos() {
        return incidentes;
    }

    @Override
    public void eliminar(Incidente incidente) {
        incidentes.remove(incidente);
    }

    @Override
    public Optional<Incidente> buscarIncidente(Incidente incidente) {
        return incidentes.stream().filter(incidente1 -> incidente1.equals(incidente)).findFirst();
    }

    @Override
    public boolean buscarFallaTecnicaEnHeladera(Heladera heladera) {
        return incidentes.stream().anyMatch(incidente -> incidente.getHeladera().equals(heladera) && incidente.getTipo().equals(TipoEvento.FALLA_TECNICA) && incidente.isResuelto());
    }
}
