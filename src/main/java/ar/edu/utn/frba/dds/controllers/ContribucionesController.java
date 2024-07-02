package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.ContribucionHumana;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.ContribucionJuridica;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import ar.edu.utn.frba.dds.models.repositories.contribuciones_humanas.ContribucionesHumanasRepository;
import ar.edu.utn.frba.dds.models.repositories.contribuciones_juridicas.ContribucionesJuridicasRepository;

public class ContribucionesController {
    private ContribucionesHumanasRepository contribucionesHumanas;
    private ContribucionesJuridicasRepository contribucionesJuridicas;

    public void registrarContribucionHumana(Humano contribuidor, ContribucionHumana contribucion) {
        contribucionesHumanas.guardar(contribucion);
        contribuidor.agregarContribucion(contribucion);
    }

    public void registrarContribucionJurdiaca(Juridica contribuidor, ContribucionJuridica contribucion) {
        contribucionesJuridicas.guardar(contribucion);
        contribuidor.agregarContribucion(contribucion);
    }
}
