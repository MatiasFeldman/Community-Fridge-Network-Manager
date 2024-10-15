package ar.edu.utn.frba.dds.models.repositories.incidentes.dao;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Incidente;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.TipoEvento;

import java.time.LocalDateTime;
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
    public void modificar(Incidente incidente) {
        Optional<Incidente> incidente1 = buscarIncidente(incidente.getId());
        incidente1.ifPresent(incidente2 -> incidentes.set(incidentes.indexOf(incidente2), incidente));
    }

    @Override
    public Optional<Incidente> buscarIncidente(Long id) {
        return incidentes
                .stream()
                .filter(incidente1 -> incidente1.getId().equals(id)).findFirst();
    }

    @Override
    public boolean buscarFallaTecnicaEnHeladera(Heladera heladera) {
        return incidentes.stream().anyMatch(incidente -> incidente.getHeladera().equals(heladera) && incidente.getTipo().equals(TipoEvento.FALLA_TECNICA) && incidente.isResuelto());
    }

    @Override
    public List<Incidente> buscarTodosPorHeladera(Heladera heladera){
        return incidentes.stream().filter(incidente -> incidente.getHeladera().equals(heladera)).toList();
    }

    @Override
    public Integer cantFallasEn(Heladera heladera) {
        return incidentes
                .stream()
                .filter(incidente -> incidente.getHeladera().equals(heladera) && incidente.getFecha().plusWeeks(1).isAfter(LocalDateTime.now()))
                .mapToInt(incidente -> 1)
                .sum();
    }
}
