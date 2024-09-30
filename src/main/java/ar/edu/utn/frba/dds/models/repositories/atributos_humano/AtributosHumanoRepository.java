package ar.edu.utn.frba.dds.models.repositories.atributos_humano;

import ar.edu.utn.frba.dds.models.entities.personas.Atributo;
import ar.edu.utn.frba.dds.models.entities.personas.TipoAtributo;
import ar.edu.utn.frba.dds.models.repositories.atributos_humano.dao.AtributosHumanoDAO;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class AtributosHumanoRepository {
    private AtributosHumanoDAO dao;

    public void guardar(Atributo atributo) {
        this.dao.guardar(atributo);
    }


    public List<Atributo> buscarTodas() {
        return this.dao.buscarTodas();
    }


    public Optional<Atributo> buscarPorId(Long id) {
        return this.dao.buscarPorId(id);
    }


    public void actualizar(Atributo atributo) {
        this.dao.actualizar(atributo);
    }


    public void eliminar(Atributo atributo) {
        this.dao.eliminar(atributo);
    }


    public List<Atributo> buscarPorTipo(TipoAtributo tipo){
        return this.dao.buscarPorTipo(tipo);
    }

    public Optional<Atributo> buscarPorNombre(String nombre) {
        return this.dao.buscarPorNombre(nombre);
    }
}
