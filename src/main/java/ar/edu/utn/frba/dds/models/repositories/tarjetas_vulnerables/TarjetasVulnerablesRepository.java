package ar.edu.utn.frba.dds.models.repositories.tarjetas_vulnerables;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.models.repositories.tarjetas_vulnerables.dao.TarjetasVulnerablesDAO;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class TarjetasVulnerablesRepository {
    private TarjetasVulnerablesDAO tarjetas;

    public Optional<TarjetaPersonaVulnerable> buscarPorId(Long idTarjetaRepartida) {
        return tarjetas.buscarPorId(idTarjetaRepartida);
    }

    public void modificar(TarjetaPersonaVulnerable tarjeta) {
        tarjetas.modificar(tarjeta);
    }

    public void guardar(TarjetaPersonaVulnerable tarjeta) {
        tarjetas.guardar(tarjeta);
    }

    public void eliminar(TarjetaPersonaVulnerable tarjeta) {
        tarjetas.eliminar(tarjeta);
    }
}
