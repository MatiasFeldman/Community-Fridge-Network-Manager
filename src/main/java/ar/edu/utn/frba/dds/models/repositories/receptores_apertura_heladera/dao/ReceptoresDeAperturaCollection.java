package ar.edu.utn.frba.dds.models.repositories.receptores_apertura_heladera.dao;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.MqttReceptorApertura;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ReceptoresDeAperturaCollection implements ReceptoresDeAperturaDAO{
    private List<MqttReceptorApertura> receptores;
    @Override
    public Optional<MqttReceptorApertura> buscarReceptorDeHeladera(UUID idHeladera) {
        return receptores
                .stream()
                .filter(r -> r.getIdHeladera().equals(idHeladera))
                .findFirst();
    }
}
