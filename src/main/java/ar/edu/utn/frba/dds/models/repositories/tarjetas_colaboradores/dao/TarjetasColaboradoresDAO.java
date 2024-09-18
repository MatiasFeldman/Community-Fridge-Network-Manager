package ar.edu.utn.frba.dds.models.repositories.tarjetas_colaboradores.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaColaborador;

import java.util.Optional;

public interface TarjetasColaboradoresDAO {
    Optional<TarjetaColaborador> buscarPorId(Long idTarjetaRepartida);

    void modificar(TarjetaColaborador tarjeta);

    void guardar(TarjetaColaborador tarjeta);

    void eliminar(TarjetaColaborador tarjeta);
}
