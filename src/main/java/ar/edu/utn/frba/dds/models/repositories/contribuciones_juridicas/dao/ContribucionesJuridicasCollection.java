package ar.edu.utn.frba.dds.models.repositories.contribuciones_juridicas.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.ContribucionJuridica;

import java.util.List;

public class ContribucionesJuridicasCollection implements ContribucionesJuridicasDAO{
    private List<ContribucionJuridica> contribucionJuridicas;

    @Override
    public void guardar(ContribucionJuridica contribucionJuridica) {
        contribucionJuridicas.add(contribucionJuridica);
    }
}
