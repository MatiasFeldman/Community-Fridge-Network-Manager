package ar.edu.utn.frba.dds.models.repositories.tecnicos.dao;

import ar.edu.utn.frba.dds.models.entities.helpers.distancia_entre_coordenadas.CalculadoraDistancia;
import ar.edu.utn.frba.dds.models.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class TecnicosCollection implements TecnicosDAO {
    private List<Tecnico> tecnicos;
    private Long currentId = 100L;

    @Override
    public void guardar(Tecnico creado) {
        creado.setId(currentId);
        this.tecnicos.add(creado);
        currentId++;
    }

    @Override
    public List<Tecnico> buscarTodos() {
        return tecnicos;
    }

    @Override
    public void eliminar(Tecnico tecnico) {
        tecnicos.remove(tecnico);
    }

    @Override
    public Optional<Tecnico> buscarPorId(Long id) {
        return tecnicos.stream().filter(tecnico -> tecnico.getId().equals(id)).findFirst();
    }
    @Override
    public Optional<Tecnico> buscarPorIdUsuario(Long id) {
        return tecnicos.stream().filter(tecnico -> tecnico.getUser().getId().equals(id)).findFirst();
    }

    @Override
    public Optional<Tecnico> buscarMasCercano(Direccion origen) {
        List<Tecnico> tecnicos = this.buscarTodos();
        if (tecnicos.isEmpty()) {
            return Optional.empty();
        } else if (tecnicos.size() == 1) {
            return Optional.of(tecnicos.get(0));
        } else if (tecnicos.stream().anyMatch(t -> t.getAreaCobertura().getDireccionRaiz() == origen)) {
            return tecnicos.stream().filter(t -> t.getAreaCobertura().getDireccionRaiz() == origen).findFirst();
        } else {
            return tecnicos.stream().min(Comparator.comparing(t -> t.distanciaA(origen)));
        }
    }

    @Override
    public void modificar(Tecnico tecnico) {
        Optional<Tecnico> t1 = this.buscarPorId(tecnico.getId());
        t1.ifPresent(t -> {
            tecnicos.remove(t);
            tecnicos.add(tecnico);
        });
    }
}
