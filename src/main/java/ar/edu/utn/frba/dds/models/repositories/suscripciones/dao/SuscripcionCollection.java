package ar.edu.utn.frba.dds.models.repositories.suscripciones.dao;

import ar.edu.utn.frba.dds.models.entities.suscripciones.SuscripcionAHeladera;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
@AllArgsConstructor
public class SuscripcionCollection implements SuscripcionDAO {
    private List<SuscripcionAHeladera> suscripcionAHeladeras;
    private Long currentId = 100L;

    @Override
    public void guardar(SuscripcionAHeladera suscripcionAHeladera) {
        suscripcionAHeladera.setId(currentId);
        this.suscripcionAHeladeras.add(suscripcionAHeladera);
        currentId++;
    }

    @Override
    public List<SuscripcionAHeladera> buscarTodos() {
        return this.suscripcionAHeladeras;
    }

    @Override
    public Optional<SuscripcionAHeladera> buscarPorId(Long id) {
        return this.suscripcionAHeladeras
                .stream()
                .filter(suscripcion -> suscripcion.getId().equals(id)).findFirst();
    }

    @Override
    public Optional<SuscripcionAHeladera> buscarPorUsuarioIdYHeladeraId(Long usuarioId, Long heladeraId) {
        return this.suscripcionAHeladeras
                .stream()
                .filter(suscripcion -> suscripcion.getObserverSuscripcion().getId().equals(usuarioId)
                        && suscripcion.getHeladera().getId().equals(heladeraId))
                .findFirst();
    }

    @Override
    public void eliminar(SuscripcionAHeladera suscripcionAHeladera) {
        this.suscripcionAHeladeras.remove(suscripcionAHeladera);
    }

    @Override
    public void modificar(SuscripcionAHeladera suscripcionAHeladera) {
        Optional<SuscripcionAHeladera> suscripcionOptional = this.buscarPorId(suscripcionAHeladera.getId());
        if (suscripcionOptional.isPresent()){
            this.suscripcionAHeladeras.remove(suscripcionOptional.get());
            this.suscripcionAHeladeras.add(suscripcionAHeladera);
        } else{
            this.guardar(suscripcionAHeladera);
        }
    }
}
