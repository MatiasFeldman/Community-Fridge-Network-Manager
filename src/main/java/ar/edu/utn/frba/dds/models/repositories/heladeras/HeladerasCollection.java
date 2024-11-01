package ar.edu.utn.frba.dds.models.repositories.heladeras;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class HeladerasCollection implements HeladerasDAO {
    private List<Heladera> heladeras;
    private Long currentId = 100L;

    @Override
    public void guardar(Heladera heladera) {
        heladera.setId(currentId);
        heladeras.add(heladera);
        currentId++;
    }

    @Override
    public List<Heladera> buscarTodos() {
        return heladeras;
    }

    @Override
    public void eliminar(Heladera heladera) {
        heladeras.remove(heladera);
    }

    @Override
    public Optional<Heladera> buscarPorNombre(String name) {
        return heladeras
                .stream()
                .filter(h -> h.getNombre().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public Optional<Heladera> buscarPorId(Long id) {
        return heladeras
                .stream()
                .filter(h -> h.getId().equals(id))
                .findFirst();
    }

    @Override
    public void modificar(Heladera heladera) {
        Optional<Heladera> heladera1 = buscarPorId(heladera.getId());
        heladera1.ifPresent(heladera2 -> heladeras.set(heladeras.indexOf(heladera2), heladera));
    }

    @Override
    public List<Heladera> buscarHeladerasPorDireccion(String valorBusqueda) {
        System.out.println("Voy a comparar la direccion de valor: " + valorBusqueda);
        return heladeras
                .stream()
                .filter(h -> h.getDireccion().getDireccion().equalsIgnoreCase(valorBusqueda))
                .toList();
    }

    @Override
    public List<Heladera> buscarHeladerasPorComuna(String valorBusqueda) {
        return heladeras
                .stream()
                .filter(h -> h.getDireccion().getComuna().getNombre().equalsIgnoreCase(valorBusqueda))
                .toList();
    }

}
