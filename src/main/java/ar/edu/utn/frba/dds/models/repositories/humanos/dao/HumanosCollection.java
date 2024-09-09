package ar.edu.utn.frba.dds.models.repositories.humanos.dao;

import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class HumanosCollection implements HumanosDAO {
    private List<Humano> humanos;


    @Override
    public void guardar(Humano humano) {
        this.humanos.add(humano);
    }

    @Override
    public List<Humano> buscarTodos() {
        return this.humanos;
    }

    @Override
    public Optional<Humano> buscarPorId(Long id) {
        return this.humanos
                .stream()
                .filter(humano -> humano.getIdUsuario().equals(id)).findFirst();
    }

    @Override
    public void eliminar(Humano humano) {
        this.humanos.remove(humano);
    }

    @Override
    public void modificar(Humano humano) {
        Optional<Humano> humanoOptional = this.buscarPorId(humano.getIdUsuario());
        humanoOptional.ifPresent(humano1 -> {
            this.eliminar(humano1);
            this.guardar(humano);
        });
    }

    @Override
    public Optional<Humano> buscarPorDocumento(String tipo, String nro) {
        return this.humanos
                .stream()
                .filter(humano -> humano.getDocumento(tipo).equals(nro))
                .findFirst();
    }

    @Override
    public boolean existeUsername(String username) {
        return this.humanos
                .stream()
                .anyMatch(humano -> humano.getUsername().equals(username));
    }
}
