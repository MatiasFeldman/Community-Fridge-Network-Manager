package ar.edu.utn.frba.dds.models.repositories.visitasHeladeras.dao;



import ar.edu.utn.frba.dds.models.entities.tecnicos.VisitaAHeladera;

import java.util.List;
import java.util.Optional;

public interface VisitaHeladeraDAO {
    void guardar(VisitaAHeladera visita);
    List<VisitaAHeladera> buscarTodas();
    Optional<VisitaAHeladera> buscarPorId(Long id);
    void actualizar(VisitaAHeladera visita);
    void eliminar(VisitaAHeladera visita);

}
