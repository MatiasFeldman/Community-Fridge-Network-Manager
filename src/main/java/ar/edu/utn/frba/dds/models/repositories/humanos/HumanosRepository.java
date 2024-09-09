package ar.edu.utn.frba.dds.models.repositories.humanos;

import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.repositories.humanos.dao.HumanosDAO;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class HumanosRepository {
    private HumanosDAO humanos;

    public void guardar(Humano humano) {
        humanos.guardar(humano);
    }

    public Optional<Humano> buscarPorId(Long id) {
        return humanos.buscarPorId(id);
    }

    public List<Humano> buscarTodos() {
        return humanos.buscarTodos();
    }

    public void eliminar(Humano humano) {
        humanos.eliminar(humano);
    }

    public Optional<Humano> buscarPorDocumento(String tipo, String nro){
        return humanos.buscarPorDocumento(tipo, nro);
    }

    public boolean existeUsername(String username){
        return humanos.existeUsername(username);
    }
}
