package ar.edu.utn.frba.dds.models.repositories.humanos.dao;

import ar.edu.utn.frba.dds.models.entities.personas.Humano;

import java.util.List;
import java.util.Optional;

public class HumanosCollection implements HumanosDAO{

    private List<Humano> humanos;
    public HumanosCollection(List<Humano> humanos) {
        this.humanos = humanos;
    }

    @Override
    public void guardar(Humano humano) {
        humanos.add(humano);
    }

    @Override
    public Optional<Humano> buscarPorIdentificacion(String nombre, String apellido){
        return humanos
                .stream()
                .filter(humano -> humano.nombre().equals(nombre) && humano.apellido().equals(apellido))
                .findFirst();
    }

    @Override
    public List<Humano> buscarTodos() {
        return humanos;
    }

    @Override
    public void eliminar(Humano humano) {
        humanos.remove(humano);
    }
}
