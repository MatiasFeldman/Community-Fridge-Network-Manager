package ar.edu.utn.frba.dds.models.repositories.visitasHeladeras.dao;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.models.entities.tecnicos.VisitaAHeladera;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
public class VisitaHeladeraCollection implements VisitaHeladeraDAO{
    private List<VisitaAHeladera> visitas;
    private Long currentId = 100L;
    @Override
    public void guardar(VisitaAHeladera visita) {
        visita.setId(currentId);
        this.visitas.add(visita);
        currentId++;
    }

    @Override
    public List<VisitaAHeladera> buscarTodas() { return this.visitas; }

    @Override
    public Optional<VisitaAHeladera> buscarPorId(Long id) {
        return visitas
                .stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findFirst();
    }

    @Override
    public void actualizar(VisitaAHeladera visita) {
        this.visitas.removeIf( c -> Objects.equals(c.getId(),visita.getId()));
        this.visitas.add(visita);
    }

    @Override
    public void eliminar(VisitaAHeladera visita) {
        this.visitas.remove(visita);
    }

    @Override
    public List<VisitaAHeladera> buscarPorHeladera(Heladera heladera) {
        return this.visitas
                .stream()
                .filter(c -> Objects.equals(c.getHeladeraFallada().getId(), heladera.getId()))
                .toList();
    }

    @Override
    public  List<VisitaAHeladera> buscarPorTecnico(Tecnico tecnico){

        return this.visitas
                .stream()
                .filter(visita -> visita.getTecnico() != null && visita.getTecnico().equals(tecnico))
                .toList();
    }
}
