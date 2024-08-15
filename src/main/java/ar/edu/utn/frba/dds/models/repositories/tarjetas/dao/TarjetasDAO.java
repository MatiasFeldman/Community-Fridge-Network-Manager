package ar.edu.utn.frba.dds.models.repositories.tarjetas.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaHumano;

import java.util.Optional;
import java.util.UUID;

public interface TarjetasDAO {
    public Optional<TarjetaHumano> buscarPorDuenio(UUID id);
}
