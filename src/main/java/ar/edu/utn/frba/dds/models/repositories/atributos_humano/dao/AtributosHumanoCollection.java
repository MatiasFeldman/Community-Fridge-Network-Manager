package ar.edu.utn.frba.dds.models.repositories.atributos_humano.dao;

import ar.edu.utn.frba.dds.models.entities.personas.Atributo;
import ar.edu.utn.frba.dds.models.entities.personas.TipoAtributo;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class AtributosHumanoCollection implements AtributosHumanoDAO {
    private List<Atributo> atributos;

    @Override
    public void guardar(Atributo atributo) {
        if (atributo.getId() == null) {
            atributo.setId((Long.valueOf(this.atributos.size() - 1)));
        }
        this.atributos.add(atributo);
    }

    @Override
    public List<Atributo> buscarTodas() {
        return this.atributos;
    }

    @Override
    public Optional<Atributo> buscarPorId(Long id) {
        return this.atributos.stream().filter(atributo -> atributo.getId().equals(id)).findFirst();
    }

    @Override
    public void actualizar(Atributo atributo) {
        this.atributos.removeIf(d -> d.getId().equals(atributo.getId()));
        this.atributos.add(atributo);
    }

    @Override
    public void eliminar(Atributo atributo) {
        this.atributos.remove(atributo);
    }

    @Override
    public List<Atributo> buscarPorTipo(TipoAtributo tipo) {
        return this.atributos.stream().filter(atributo -> atributo.getTipo().equals(tipo)).toList();
    }

    @Override
    public Optional<Atributo> buscarPorNombre(String nombre) {
        return this.atributos.stream().filter(atributo -> atributo.getNombre().equals(nombre)).findFirst();
    }
}
