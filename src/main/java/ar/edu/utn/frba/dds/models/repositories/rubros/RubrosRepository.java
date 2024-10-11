package ar.edu.utn.frba.dds.models.repositories.rubros;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Rubro;
import ar.edu.utn.frba.dds.models.repositories.rubros.dao.RubroDAO;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class RubrosRepository {
    private RubroDAO rubros;

    public void guardar(Rubro rubro){rubros.guardar(rubro);}

    public void eliminar(Rubro rubro){rubros.eliminar(rubro);}

    public void modificar(Rubro rubro){rubros.modificar(rubro);}

    public List<Rubro> buscarTodos(){return rubros.buscarTodos();}

    public Optional<Rubro> buscarPorId(Long id){return rubros.buscarPorId(id);}

}
