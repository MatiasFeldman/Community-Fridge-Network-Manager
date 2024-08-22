package ar.edu.utn.frba.dds.models.repositories.tarjetas_vulnerables;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.models.repositories.tarjetas_vulnerables.dao.TarjetasVulnerablesDAO;

import java.util.Optional;

public class TarjetasVulnerablesRepository {
    private TarjetasVulnerablesDAO tarjetasVulnerablesDAO;

    public Optional<TarjetaPersonaVulnerable> buscarPorId(String id) {
        return tarjetasVulnerablesDAO.buscarTarjetaPorId(id);
    }
}
