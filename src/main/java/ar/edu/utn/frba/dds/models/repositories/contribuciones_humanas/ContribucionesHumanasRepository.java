package ar.edu.utn.frba.dds.models.repositories.contribuciones_humanas;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.ContribucionHumana;
import ar.edu.utn.frba.dds.models.repositories.contribuciones_humanas.dao.ContribucionesHumanasDAO;

public class ContribucionesHumanasRepository {
    private ContribucionesHumanasDAO dao;

    public void guardar(ContribucionHumana contribucionHumana) {
        dao.guardar(contribucionHumana);
    }
}
