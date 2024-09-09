package ar.edu.utn.frba.dds.models.repositories.juridicas;

import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import ar.edu.utn.frba.dds.models.repositories.juridicas.dao.JuridicasCollection;
import ar.edu.utn.frba.dds.models.repositories.juridicas.dao.JuridicasDAO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JuridicasRepository {
    private JuridicasDAO dao;

    public Optional<Juridica> buscarPorId(Long id) {
        return dao.buscarPorId(id);
    }

    public void guardar(Juridica juridica){dao.guardar(juridica);}

    public void eliminar(Juridica juridica){dao.eliminar(juridica);}

    void modificar(Juridica juridica){dao.modificar(juridica);}

    public List<Juridica> buscarTodos(){return dao.buscarTodos();}

}
