package ar.edu.utn.frba.dds.models.repositories.tarjetas.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Tarjeta;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TipoTarjeta;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.Optional;

public class TarjetasDB implements TarjetasDAO, WithSimplePersistenceUnit {

    @Override
    public Optional<Tarjeta> buscarPorId(Long idTarjetaRepartida) {
        return Optional.ofNullable(entityManager().find(Tarjeta.class, idTarjetaRepartida));
    }

    @Override
    public void modificar(Tarjeta tarjeta) {
        withTransaction(() -> {
            entityManager().merge(tarjeta);
        });
    }

    @Override
    public void guardar(Tarjeta tarjeta) {
        withTransaction(() -> {
            entityManager().persist(tarjeta);
        });
    }

    @Override
    public void eliminar(Tarjeta tarjeta) {
        withTransaction(() -> {
            entityManager().remove(tarjeta);
        });
    }
}
