package ar.edu.utn.frba.dds.models.repositories.tarjetas;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaHumano;
import ar.edu.utn.frba.dds.models.repositories.tarjetas.dao.TarjetasDAO;

import java.util.Optional;
import java.util.UUID;

public class TarjetasRepository {
    private TarjetasDAO tarjetas;

    public Optional<TarjetaHumano> buscarTarjetaPorDuenio(UUID id) {
        return tarjetas.buscarPorDuenio(id);
    }
}
