package ar.edu.utn.frba.dds.models.repositories.visitasHeladeras;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.models.entities.tecnicos.VisitaAHeladera;
import ar.edu.utn.frba.dds.models.repositories.visitasHeladeras.dao.VisitaHeladeraDAO;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class VisitaHeladeraRepository {
    private VisitaHeladeraDAO dao;

    public void guardar(VisitaAHeladera visita) {
        this.dao.guardar(visita);
    }

    public List<VisitaAHeladera> buscarTodas() {
        return dao.buscarTodas();
    }


    public Optional<VisitaAHeladera> buscarPorId(Long id) {
        return dao.buscarPorId(id);
    }

    public void actualizar(VisitaAHeladera visita) {
        this.dao.actualizar(visita);
    }

    public void eliminar(VisitaAHeladera visita) {
        this.dao.eliminar(visita);
    }

    public List<VisitaAHeladera> buscarPorHeladera(Heladera heladera) {
        return dao.buscarPorHeladera(heladera);
    }

    public List<VisitaAHeladera> buscarPorTecnico(Tecnico tecnico) {
        return dao.buscarPorTecnico(tecnico);
    }
}
