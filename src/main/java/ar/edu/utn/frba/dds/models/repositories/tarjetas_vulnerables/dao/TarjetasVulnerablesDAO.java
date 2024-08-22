package ar.edu.utn.frba.dds.models.repositories.tarjetas_vulnerables.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaPersonaVulnerable;

import java.util.Optional;

public interface TarjetasVulnerablesDAO {

    public Optional<TarjetaPersonaVulnerable> buscarTarjetaPorId(String id);

}
