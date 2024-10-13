package ar.edu.utn.frba.dds.models.repositories.donacion_dinero.DAO;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.DonacionDeDinero;

import java.util.List;
import java.util.Optional;

public interface DonacionDineroDAO {
    void guardar(DonacionDeDinero donacionDeDinero);
    List<DonacionDeDinero> buscarTodas();
    Optional<DonacionDeDinero> buscarPorId(Long id);
    void actualizar(DonacionDeDinero donacionDeDinero);
    void eliminar(DonacionDeDinero donacionDeDinero);
    List<DonacionDeDinero> buscarPorColaborador(Long id);
}
