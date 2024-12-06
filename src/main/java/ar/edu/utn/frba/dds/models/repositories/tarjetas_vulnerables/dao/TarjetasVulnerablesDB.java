package ar.edu.utn.frba.dds.models.repositories.tarjetas_vulnerables.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaPersonaVulnerable;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class TarjetasVulnerablesDB implements TarjetasVulnerablesDAO, WithSimplePersistenceUnit {

    @Override
    public Optional<TarjetaPersonaVulnerable> buscarPorId(Long idTarjetaRepartida) {
        TarjetaPersonaVulnerable tarjeta = entityManager().find(TarjetaPersonaVulnerable.class, idTarjetaRepartida);
        if (tarjeta != null) {
            entityManager().refresh(tarjeta); // Forzar sincronización de la entidad
        }
        return Optional.ofNullable(tarjeta);
    }

    @Override
    public void modificar(TarjetaPersonaVulnerable tarjeta) {
        withTransaction(() -> {
            entityManager().merge(tarjeta);
        });
    }

    @Override
    public void guardar(TarjetaPersonaVulnerable tarjeta) {
        tarjeta.setPresente(true);
        withTransaction(() -> {
            entityManager().persist(tarjeta);
        });
    }

    @Override
    public void eliminar(TarjetaPersonaVulnerable tarjeta) {
        tarjeta.setPresente(false);
        this.modificar(tarjeta);
    }

    @Override
    public List<TarjetaPersonaVulnerable> buscarTodas() {
        List<TarjetaPersonaVulnerable> tarjetas = entityManager()
                .createQuery("select t from TarjetaPersonaVulnerable t where t.presente = true", TarjetaPersonaVulnerable.class)
                .getResultList();

        tarjetas.forEach(t -> entityManager().refresh(t)); // Forzar sincronización de todas las entidades
        return tarjetas;
    }
}

