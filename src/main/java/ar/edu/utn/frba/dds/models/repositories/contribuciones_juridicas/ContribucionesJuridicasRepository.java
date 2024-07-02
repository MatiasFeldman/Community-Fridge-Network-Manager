package ar.edu.utn.frba.dds.models.repositories.contribuciones_juridicas;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.ContribucionJuridica;
import ar.edu.utn.frba.dds.models.repositories.contribuciones_juridicas.dao.ContribucionesJuridicasDAO;

public class ContribucionesJuridicasRepository {
    private ContribucionesJuridicasDAO dao;

    public void guardar(ContribucionJuridica contribucionJuridica) {
        dao.guardar(contribucionJuridica);
    }
}
