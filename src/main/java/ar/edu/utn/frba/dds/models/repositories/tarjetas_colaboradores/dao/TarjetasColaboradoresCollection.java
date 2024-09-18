package ar.edu.utn.frba.dds.models.repositories.tarjetas_colaboradores.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Tarjeta;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaColaborador;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class TarjetasColaboradoresCollection implements TarjetasColaboradoresDAO, WithSimplePersistenceUnit {
    private List<TarjetaColaborador> tarjetas;

    @Override
    public Optional<TarjetaColaborador> buscarPorId(Long idTarjetaRepartida) {
        return tarjetas
                .stream()
                .filter(t -> t.getId().equals(idTarjetaRepartida))
                .findFirst();
    }

    @Override
    public void modificar(TarjetaColaborador tarjeta) {
        Optional<TarjetaColaborador> tarjetaOptional = buscarPorId(tarjeta.getId());
        tarjetaOptional.ifPresent(t -> {
            tarjetas.remove(t);
            tarjetas.add(tarjeta);
        });
    }

    @Override
    public void guardar(TarjetaColaborador tarjeta) {
        tarjetas.add(tarjeta);
    }

    @Override
    public void eliminar(TarjetaColaborador tarjeta) {
        tarjetas.remove(tarjeta);
    }
}
