package ar.edu.utn.frba.dds.models.repositories.tecnicos;

import ar.edu.utn.frba.dds.models.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.models.repositories.tecnicos.dao.TecnicosDAO;

import java.util.List;
import java.util.Optional;

public class TecnicosRepository {
    TecnicosDAO tecnicosDAO;
    public void guardar(Tecnico tecnico) {tecnicosDAO.guardar(tecnico);}

    public List<Tecnico> buscarTodos(){return tecnicosDAO.buscarTodos();}
    public void eliminar(Tecnico tecnico){tecnicosDAO.eliminar(tecnico);}
    public Optional<Tecnico> buscarPorId(Long id){return tecnicosDAO.buscarPorId(id);}
    public void modificar(Tecnico tecnico){tecnicosDAO.modificar(tecnico);}

    public Optional<Tecnico> buscarMasCercano(Direccion origen) {
        return tecnicosDAO.buscarMasCercano(origen);
    }
}
