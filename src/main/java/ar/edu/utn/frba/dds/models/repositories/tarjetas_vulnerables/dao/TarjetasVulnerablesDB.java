package ar.edu.utn.frba.dds.models.repositories.tarjetas_vulnerables.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Tarjeta;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaPersonaVulnerable;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.Optional;

public class TarjetasVulnerablesDB implements TarjetasVulnerablesDAO, WithSimplePersistenceUnit {

    @Override
    public Optional<TarjetaPersonaVulnerable> buscarPorId(Long idTarjetaRepartida) {
        return Optional.ofNullable(entityManager().find(TarjetaPersonaVulnerable.class, idTarjetaRepartida));
    }

    @Override
    public void modificar(TarjetaPersonaVulnerable tarjeta) {
        withTransaction(() -> {
            entityManager().merge(tarjeta);
        });
    }

    @Override
    public void guardar(TarjetaPersonaVulnerable tarjeta) {
        withTransaction(() -> {
            entityManager().persist(tarjeta);
        });
    }

    @Override
    public void eliminar(TarjetaPersonaVulnerable tarjeta) {
        withTransaction(() -> {
            entityManager().remove(tarjeta);
        });
    }
}
