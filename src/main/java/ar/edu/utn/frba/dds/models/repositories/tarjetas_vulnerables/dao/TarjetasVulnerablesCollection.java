package ar.edu.utn.frba.dds.models.repositories.tarjetas_vulnerables.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaPersonaVulnerable;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
@AllArgsConstructor
public class TarjetasVulnerablesCollection implements TarjetasVulnerablesDAO {
    private List<TarjetaPersonaVulnerable> tarjetas;
    private Long currentId = 100L;

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
        tarjeta.setId(currentId);
        tarjetas.add(tarjeta);
        currentId++;
    }

    @Override
    public void eliminar(TarjetaPersonaVulnerable tarjeta) {
        tarjetas.remove(tarjeta);
    }

    @Override
    public List<TarjetaPersonaVulnerable> buscarTodas() {
        return tarjetas;
    }
}
