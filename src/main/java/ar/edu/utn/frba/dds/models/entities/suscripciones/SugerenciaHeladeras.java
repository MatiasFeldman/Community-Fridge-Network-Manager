package ar.edu.utn.frba.dds.models.entities.suscripciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;

import java.util.List;

public class SugerenciaHeladeras {
    private HeladerasRepository heladerasRepository;

    public SugerenciaHeladeras(HeladerasRepository heladerasRepository) {
        this.heladerasRepository = heladerasRepository;
    }

    public List<Heladera> sugerirHeladeras(Heladera heladeraBase) {
        return heladerasRepository.buscarTodos()
                .stream()
                .filter(heladera -> heladera.getDireccion().esCercaDe(heladeraBase.getDireccion()) && heladera.getCapActual() > heladeraBase.getCapActual())
                .toList();
    }
}