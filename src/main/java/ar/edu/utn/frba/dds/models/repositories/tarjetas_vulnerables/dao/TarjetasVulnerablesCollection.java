package ar.edu.utn.frba.dds.models.repositories.tarjetas_vulnerables.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Tarjeta;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaPersonaVulnerable;

import java.util.List;
import java.util.Optional;

public class TarjetasVulnerablesCollection implements TarjetasVulnerablesDAO {
    private List<TarjetaPersonaVulnerable> tarjetas;

    @Override
    public Optional<TarjetaPersonaVulnerable> buscarPorId(Long idTarjetaRepartida) {
        return tarjetas
                .stream()
                .filter(t -> t.getId().equals(idTarjetaRepartida))
                .findFirst();
    }

    @Override
    public void modificar(TarjetaPersonaVulnerable tarjeta) {
        Optional<TarjetaPersonaVulnerable> tarjetaOptional = buscarPorId(tarjeta.getId());
        tarjetaOptional.ifPresent(t -> {
            tarjetas.remove(t);
            tarjetas.add(tarjeta);
        });
    }

    @Override
    public void guardar(TarjetaPersonaVulnerable tarjeta) {
        tarjetas.add(tarjeta);
    }

    @Override
    public void eliminar(TarjetaPersonaVulnerable tarjeta) {
        tarjetas.remove(tarjeta);
    }
}
