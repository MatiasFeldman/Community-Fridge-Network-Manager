package ar.edu.utn.frba.dds.models.repositories.tarjetas;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Tarjeta;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaHumano;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TipoTarjeta;
import ar.edu.utn.frba.dds.models.repositories.tarjetas.dao.TarjetasDAO;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class TarjetasRepository {
    private TarjetasDAO tarjetas;

    public Optional<Tarjeta> buscarPorId(Long idTarjetaRepartida) {
        return tarjetas.buscarPorId(idTarjetaRepartida);
    }

    public void modificar(Tarjeta tarjeta){tarjetas.modificar(tarjeta);}

    public void guardar(Tarjeta tarjeta){tarjetas.guardar(tarjeta);}

    public void eliminar(Tarjeta tarjeta){tarjetas.eliminar(tarjeta);}
}
