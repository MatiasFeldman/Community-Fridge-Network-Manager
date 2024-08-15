package ar.edu.utn.frba.dds.models.repositories.tarjetas.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaHumano;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TarjetasCollection implements TarjetasDAO{
    private List<TarjetaHumano> tarjetas;

    @Override
    public Optional<TarjetaHumano> buscarPorDuenio(UUID id) {
        return tarjetas
                .stream()
                .filter(t -> t.getDuenio().getIdUsuario().equals(id))
                .findFirst();
    }
}
