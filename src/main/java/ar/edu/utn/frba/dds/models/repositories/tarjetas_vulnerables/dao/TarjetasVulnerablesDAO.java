package ar.edu.utn.frba.dds.models.repositories.tarjetas_vulnerables.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaPersonaVulnerable;

import java.util.List;
import java.util.Optional;

public interface TarjetasVulnerablesDAO {

    Optional<TarjetaPersonaVulnerable> buscarPorId(Long idTarjetaRepartida);

    void modificar(TarjetaPersonaVulnerable tarjeta);

    void guardar(TarjetaPersonaVulnerable tarjeta);

    void eliminar(TarjetaPersonaVulnerable tarjeta);

    List<TarjetaPersonaVulnerable> buscarTodas();
}