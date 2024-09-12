package ar.edu.utn.frba.dds.models.repositories.donaciones_de_vianda;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.DonacionDeVianda;
import ar.edu.utn.frba.dds.models.repositories.donaciones_de_vianda.dao.DonacionesDeViandaDAO;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class DonacionesDeViandaRepository {
    private DonacionesDeViandaDAO dao;



    public void guardar(DonacionDeVianda donacionDeVianda) {
        this.dao.guardar(donacionDeVianda);
    }


    public List<DonacionDeVianda> buscarTodas() {
        return dao.buscarTodas();
    }


    public Optional<DonacionDeVianda> buscarPorId(Long id) {
        return dao.buscarPorId(id);
    }

    public void actualizar(DonacionDeVianda donacionDeVianda) {
        this.dao.actualizar(donacionDeVianda);
    }


    public void eliminar(DonacionDeVianda donacionDeVianda) {
        this.dao.eliminar(donacionDeVianda);
    }


    public List<DonacionDeVianda> buscarPorColaborador(Long id) {
        return dao.buscarPorColaborador(id);
    }
}
