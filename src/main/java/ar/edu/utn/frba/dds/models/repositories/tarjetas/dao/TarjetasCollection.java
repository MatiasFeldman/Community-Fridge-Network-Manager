package ar.edu.utn.frba.dds.models.repositories.tarjetas.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Tarjeta;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaHumano;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TarjetasCollection implements TarjetasDAO{
    private List<Tarjeta> tarjetas;

    @Override
    public Optional<Tarjeta> buscarPorDuenio(UUID id) {
        return tarjetas
                .stream()
                .filter(t -> t.getDuenioId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Tarjeta> buscarPorId(String idTarjetaRepartida) {
        return tarjetas
                .stream()
                .filter(t -> t.getId().equals(idTarjetaRepartida))
                .findFirst();
    }
}
