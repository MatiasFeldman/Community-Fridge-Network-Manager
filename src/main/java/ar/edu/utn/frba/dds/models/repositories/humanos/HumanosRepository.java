package ar.edu.utn.frba.dds.models.repositories.humanos;

import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.repositories.humanos.dao.HumanosDAO;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class HumanosRepository {
    private HumanosDAO humanos;

    public void guardar(ColaboradorHumano colaboradorHumano) {
        humanos.guardar(colaboradorHumano);
    }

    public Optional<ColaboradorHumano> buscarPorIdUsuario(Long id) {
        return humanos.buscarPorIdUsuario(id);
    }

    public List<ColaboradorHumano> buscarTodos() {
        return humanos.buscarTodos();
    }

    public void eliminar(ColaboradorHumano colaboradorHumano) {
        humanos.eliminar(colaboradorHumano);
    }

    public Optional<ColaboradorHumano> buscarPorDocumento(String tipo, String nro) {
        return humanos.buscarPorDocumento(tipo, nro);
    }

    public boolean existeUsername(String username) {
        return humanos.existeUsername(username);
    }

    public void actualizar(ColaboradorHumano humano) {
        humanos.modificar(humano);
    }

    public Optional<ColaboradorHumano> buscarPorTarjeta(Long id) {
        return humanos.buscarPorTarjeta(id);
    }
}
