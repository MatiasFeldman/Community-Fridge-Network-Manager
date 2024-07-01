package ar.edu.utn.frba.dds.models.repositories.receptores_de_temperatura;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.ReceptorTemperatura;

import java.util.List;

public class ReceptoresDeTempRepository {
    private List<ReceptorTemperatura> receptores;
    public List<ReceptorTemperatura> buscarTodos() {
        return receptores;
    }
}
