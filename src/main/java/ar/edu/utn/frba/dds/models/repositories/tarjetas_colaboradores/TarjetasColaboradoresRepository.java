package ar.edu.utn.frba.dds.models.repositories.tarjetas_colaboradores;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaColaborador;
import ar.edu.utn.frba.dds.models.repositories.tarjetas_colaboradores.dao.TarjetasColaboradoresDAO;

import java.util.List;
import java.util.Optional;

public class TarjetasColaboradoresRepository {
    private TarjetasColaboradoresDAO tarjetas;

    public Optional<TarjetaColaborador> buscarPorId(Long idTarjetaRepartida) {
        return tarjetas.buscarPorId(idTarjetaRepartida);
    }

    public void modificar(TarjetaColaborador tarjeta) {
        tarjetas.modificar(tarjeta);
    }

    public void guardar(TarjetaColaborador tarjeta) {
        tarjetas.guardar(tarjeta);
    }

    public void eliminar(TarjetaColaborador tarjeta) {
        tarjetas.eliminar(tarjeta);
    }

    public List<TarjetaColaborador> buscarTodas(){
        return tarjetas.buscarTodas();
    }
}
