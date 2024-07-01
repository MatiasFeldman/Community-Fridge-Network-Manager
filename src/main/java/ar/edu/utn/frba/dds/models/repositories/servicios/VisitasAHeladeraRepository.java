package ar.edu.utn.frba.dds.models.repositories.servicios;

import ar.edu.utn.frba.dds.models.entities.tecnicos.VisitaAHeladera;
import ar.edu.utn.frba.dds.models.repositories.servicios.dao.VisitasDAO;

public class VisitasAHeladeraRepository {
    private VisitasDAO serviciosRealizados;

    public void guardar(VisitaAHeladera visita){
        serviciosRealizados.guardar(visita);
    }
}
