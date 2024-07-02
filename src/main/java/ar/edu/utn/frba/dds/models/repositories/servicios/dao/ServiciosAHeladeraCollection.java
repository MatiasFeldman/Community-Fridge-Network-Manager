package ar.edu.utn.frba.dds.models.repositories.servicios.dao;

import ar.edu.utn.frba.dds.models.entities.tecnicos.VisitaAHeladera;

import java.util.List;

public class ServiciosAHeladeraCollection implements VisitasDAO {
    private List<VisitaAHeladera> serviciosRealizados;

    @Override
    public void guardar(VisitaAHeladera visita) {
        serviciosRealizados.add(visita);
    }
}
