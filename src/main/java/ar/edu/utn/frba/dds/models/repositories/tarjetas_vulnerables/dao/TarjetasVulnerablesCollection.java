package ar.edu.utn.frba.dds.models.repositories.tarjetas_vulnerables.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaPersonaVulnerable;

import java.util.List;
import java.util.Optional;

public class TarjetasVulnerablesCollection implements TarjetasVulnerablesDAO {
    private List<TarjetaPersonaVulnerable> tarjetas;

    @Override
    public Optional<TarjetaPersonaVulnerable> buscarTarjetaPorId(String id) {
        return tarjetas.stream().filter(tarjeta -> tarjeta.getId().equals(id) && tarjeta.getDuenio() == null ).findFirst();
    }
}
