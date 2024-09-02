package ar.edu.utn.frba.dds.models.repositories.receptores_apertura_heladera;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.MqttReceptorApertura;
import ar.edu.utn.frba.dds.models.repositories.receptores_apertura_heladera.dao.ReceptoresDeAperturaDAO;

import java.util.Optional;
import java.util.UUID;

public class ReceptoresDeAperturaRepository {
    private ReceptoresDeAperturaDAO dao;

    public Optional<MqttReceptorApertura> buscarReceptorDeHeladera(Long idHeladera){
        return dao.buscarReceptorDeHeladera(idHeladera);
    }
}
