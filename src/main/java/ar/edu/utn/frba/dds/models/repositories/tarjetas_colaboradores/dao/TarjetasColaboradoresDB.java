package ar.edu.utn.frba.dds.models.repositories.tarjetas_colaboradores.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaColaborador;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class TarjetasColaboradoresDB implements TarjetasColaboradoresDAO, WithSimplePersistenceUnit {
    @Override
    public Optional<TarjetaColaborador> buscarPorId(Long idTarjetaRepartida) {
        return Optional.ofNullable(entityManager().find(TarjetaColaborador.class, idTarjetaRepartida));
    }

    @Override
    public void modificar(TarjetaColaborador tarjeta) {
        withTransaction(() -> {
            entityManager().merge(tarjeta);
        });
    }

    @Override
    public void guardar(TarjetaColaborador tarjeta) {
        tarjeta.setPresente(true);
        beginTransaction();
            entityManager().persist(tarjeta);
        commitTransaction();
    }

    @Override
    public void eliminar(TarjetaColaborador tarjeta) {
        tarjeta.setPresente(false);
        this.modificar(tarjeta);
    }

    @Override
    public List<TarjetaColaborador> buscarTodas() {
        return entityManager()
                .createQuery("select t from TarjetaColaborador t where t.presente = true", TarjetaColaborador.class)
                .getResultList();
    }
}
