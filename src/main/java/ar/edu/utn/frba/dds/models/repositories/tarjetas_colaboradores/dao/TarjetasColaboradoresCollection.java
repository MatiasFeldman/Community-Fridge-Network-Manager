package ar.edu.utn.frba.dds.models.repositories.tarjetas_colaboradores.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaColaborador;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class TarjetasColaboradoresCollection implements TarjetasColaboradoresDAO, WithSimplePersistenceUnit {
    private List<TarjetaColaborador> tarjetas;
    private Long currentId = 100L;

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
        tarjeta.setId(currentId);
        tarjetas.add(tarjeta);
        currentId++;
    }

    @Override
    public void eliminar(TarjetaColaborador tarjeta) {
        tarjetas.remove(tarjeta);
    }

    @Override
    public List<TarjetaColaborador> buscarTodas() {
        return tarjetas;
    }
}
