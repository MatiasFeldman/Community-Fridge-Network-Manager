package ar.edu.utn.frba.dds.models.repositories.tarjetas;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Tarjeta;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaHumano;
import ar.edu.utn.frba.dds.models.repositories.tarjetas.dao.TarjetasDAO;

import java.util.Optional;
import java.util.UUID;

public class TarjetasRepository {
    private TarjetasDAO tarjetas;

    public Optional<Tarjeta> buscarTarjetaPorDuenio(UUID id) {
        return tarjetas.buscarPorDuenio(id);
    }

    public Optional<Tarjeta> buscarPorId(String idTarjetaRepartida) {
        return tarjetas.buscarPorId(idTarjetaRepartida);
    }
}
