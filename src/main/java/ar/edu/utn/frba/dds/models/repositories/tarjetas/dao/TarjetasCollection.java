package ar.edu.utn.frba.dds.models.repositories.tarjetas.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Tarjeta;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaHumano;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TipoTarjeta;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TarjetasCollection implements TarjetasDAO {
    private List<Tarjeta> tarjetas;

    @Override
    public Optional<Tarjeta> buscarPorId(Long idTarjetaRepartida) {
        return tarjetas
                .stream()
                .filter(t -> t.getId().equals(idTarjetaRepartida))
                .findFirst();
    }

    @Override
    public void modificar(Tarjeta tarjeta) {
        Optional<Tarjeta> tarjetaOptional = buscarPorId(tarjeta.getId());
        tarjetaOptional.ifPresent(t -> {
            tarjetas.remove(t);
            tarjetas.add(tarjeta);
        });
    }

    @Override
    public void guardar(Tarjeta tarjeta) {
        tarjetas.add(tarjeta);
    }

    @Override
    public void eliminar(Tarjeta tarjeta) {
        tarjetas.remove(tarjeta);
    }
}
