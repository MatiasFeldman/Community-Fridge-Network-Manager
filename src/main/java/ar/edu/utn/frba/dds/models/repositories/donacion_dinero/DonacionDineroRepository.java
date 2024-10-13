package ar.edu.utn.frba.dds.models.repositories.donacion_dinero;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.DonacionDeDinero;
import ar.edu.utn.frba.dds.models.repositories.donacion_dinero.DAO.DonacionDineroDAO;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
@AllArgsConstructor
public class DonacionDineroRepository {
    private DonacionDineroDAO dao;


    public void guardar(DonacionDeDinero donacionDeDinero) {
        this.dao.guardar(donacionDeDinero);
    }


    public List<DonacionDeDinero> buscarTodas() {
        return this.dao.buscarTodas();
    }


    public Optional<DonacionDeDinero> buscarPorId(Long id) {
        return this.dao.buscarPorId(id);
    }


    public void actualizar(DonacionDeDinero donacionDeDinero) {
        this.dao.actualizar(donacionDeDinero);
    }


    public void eliminar(DonacionDeDinero donacionDeDinero) {
        this.dao.eliminar(donacionDeDinero);
    }


    public List<DonacionDeDinero> buscarPorColaborador(Long id) {
        return this.dao.buscarPorColaborador(id);
    }
}
