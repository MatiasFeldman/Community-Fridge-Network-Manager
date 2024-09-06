package ar.edu.utn.frba.dds.models.repositories.tarjetas.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Tarjeta;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaHumano;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TipoTarjeta;

import java.util.Optional;
import java.util.UUID;

public interface TarjetasDAO {
    public Optional<Tarjeta> buscarPorDuenio(Long id, TipoTarjeta tipo);

    Optional<Tarjeta> buscarPorId(Long idTarjetaRepartida);
}
