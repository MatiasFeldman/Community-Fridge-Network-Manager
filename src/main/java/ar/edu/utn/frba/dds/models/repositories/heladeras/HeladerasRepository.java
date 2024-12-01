package ar.edu.utn.frba.dds.models.repositories.heladeras;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.tecnicos.VisitaAHeladera;

import java.util.List;
import java.util.Optional;

public class HeladerasRepository {
    private HeladerasDAO heladeras;

    public HeladerasRepository(HeladerasDAO heladeras) {
        this.heladeras = heladeras;
    }

    public void guardar(Heladera heladera) {
        heladeras.guardar(heladera);
    }

    public List<Heladera> buscarTodos() {
        return heladeras.buscarTodos();
    }

    public void eliminar(Heladera heladera) {
        heladeras.eliminar(heladera);
    }

    public Optional<Heladera> buscarPorNombre(String name){
        return heladeras.buscarPorNombre(name);
    }

    public Optional<Heladera> buscarPorId(Long id){
        return heladeras.buscarPorId(id);
    }

    public void modificar(Heladera heladera){
        this.heladeras.modificar(heladera);
        System.out.println("Heladera modificada");
    }

    public List<Heladera> buscarHeladerasPorDireccion(String valorBusqueda) {
        return heladeras.buscarHeladerasPorDireccion(valorBusqueda);
    }

    public List<Heladera> buscarPorComuna(String valorBusqueda) {
        return heladeras.buscarHeladerasPorComuna(valorBusqueda);
    }

}
