package ar.edu.utn.frba.dds.models.repositories.receptores_apertura_heladera.dao;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.MqttReceptorApertura;

import java.util.Optional;
import java.util.UUID;

public interface ReceptoresDeAperturaDAO {
    Optional<MqttReceptorApertura> buscarReceptorDeHeladera(UUID idHeladera);
}
