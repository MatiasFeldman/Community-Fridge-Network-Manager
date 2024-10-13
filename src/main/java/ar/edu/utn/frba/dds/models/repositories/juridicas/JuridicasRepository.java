package ar.edu.utn.frba.dds.models.repositories.juridicas;

import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import ar.edu.utn.frba.dds.models.repositories.juridicas.dao.JuridicasDAO;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class JuridicasRepository {
    private JuridicasDAO dao;

    public Optional<Juridica> buscarPorIdUsuario(Long id) {
        return dao.buscarPorIdUsuario(id);
    }

    public void guardar(Juridica juridica){dao.guardar(juridica);}

    public void eliminar(Juridica juridica){dao.eliminar(juridica);}

    public void modificar(Juridica juridica){dao.modificar(juridica);}

    public List<Juridica> buscarTodos(){return dao.buscarTodos();}

    public Boolean existeUsername(String username){
        return dao.existeUsername(username);
    }

}
