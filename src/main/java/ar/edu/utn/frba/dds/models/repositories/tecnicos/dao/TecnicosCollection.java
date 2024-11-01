package ar.edu.utn.frba.dds.models.repositories.tecnicos.dao;

import ar.edu.utn.frba.dds.models.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class TecnicosCollection implements TecnicosDAO{
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
    public Optional<Tecnico> buscarMasCercano(Direccion origen) {
        Optional<Tecnico> tecnicoConMismaDirec = tecnicos.stream().filter(tecnico -> tecnico.getAreaCobertura().getDireccionRaiz().equals(origen)).findFirst();
        if (tecnicoConMismaDirec.isPresent()) {
            return tecnicoConMismaDirec;
        } else {
            List<Tecnico> tecnicosQuePuedenIr = tecnicos
                    .stream()
                    .filter(t -> t.puedeIrA(origen))
                    .toList();
            if (tecnicosQuePuedenIr.isEmpty()) {
                return Optional.empty();
            } else {
                return tecnicosQuePuedenIr
                        .stream()
                        .min(Comparator.comparing(t -> t.distanciaA(origen)));
            }
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
