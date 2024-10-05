package ar.edu.utn.frba.dds.models.repositories.donaciones_de_vianda.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.DonacionDeVianda;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;

import java.util.List;
import java.util.Optional;

public interface DonacionesDeViandaDAO {
    void guardar(DonacionDeVianda donacionDeVianda);
    List<DonacionDeVianda> buscarTodas();
    Optional<DonacionDeVianda> buscarPorId(Long id);
    void actualizar(DonacionDeVianda donacionDeVianda);
    void eliminar(DonacionDeVianda donacionDeVianda);
    List<DonacionDeVianda> buscarPorColaborador(Long id);

    Integer cantViandasDonadasPor(ColaboradorHumano colaborador);
}
