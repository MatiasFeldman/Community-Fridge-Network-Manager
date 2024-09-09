package ar.edu.utn.frba.dds.models.repositories.tarjetas.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Tarjeta;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaHumano;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TipoTarjeta;

import java.util.Optional;
import java.util.UUID;

public interface TarjetasDAO {

    Optional<Tarjeta> buscarPorId(Long idTarjetaRepartida);

    void modificar(Tarjeta tarjeta);

    void guardar(Tarjeta tarjeta);

    void eliminar(Tarjeta tarjeta);
}