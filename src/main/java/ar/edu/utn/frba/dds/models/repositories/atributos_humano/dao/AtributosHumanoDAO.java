package ar.edu.utn.frba.dds.models.repositories.atributos_humano.dao;

import ar.edu.utn.frba.dds.models.entities.personas.Atributo;
import ar.edu.utn.frba.dds.models.entities.personas.TipoAtributo;

import java.util.List;
import java.util.Optional;

public interface AtributosHumanoDAO {
    public void guardar(Atributo atributo);

    public List<Atributo> buscarTodas();

    public Optional<Atributo> buscarPorId(Long id);

    public void actualizar(Atributo atributo);

    public void eliminar(Atributo atributo);

    public List<Atributo> buscarPorTipo(TipoAtributo tipo);
}
