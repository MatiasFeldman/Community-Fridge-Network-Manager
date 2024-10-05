package ar.edu.utn.frba.dds.models.repositories.humanos.dao;

import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class HumanosCollection implements HumanosDAO {
    private List<ColaboradorHumano> colaboradorHumanos;


    @Override
    public void guardar(ColaboradorHumano colaboradorHumano) {
        colaboradorHumano.setId((long) (colaboradorHumanos.size() + 1));
        this.colaboradorHumanos.add(colaboradorHumano);
    }

    @Override
    public List<ColaboradorHumano> buscarTodos() {
        return this.colaboradorHumanos;
    }

    @Override
    public Optional<ColaboradorHumano> buscarPorId(Long id) {
        return this.colaboradorHumanos
                .stream()
                .filter(humano -> humano.getIdUsuario().equals(id)).findFirst();
    }

    @Override
    public void eliminar(ColaboradorHumano colaboradorHumano) {
        this.colaboradorHumanos.remove(colaboradorHumano);
    }

    @Override
    public void modificar(ColaboradorHumano colaboradorHumano) {
        Optional<ColaboradorHumano> humanoOptional = this.buscarPorId(colaboradorHumano.getIdUsuario());
        humanoOptional.ifPresent(humano1 -> {
            this.eliminar(humano1);
            this.guardar(colaboradorHumano);
        });
    }

    @Override
    public Optional<ColaboradorHumano> buscarPorDocumento(String tipo, String nro) {
        return this.colaboradorHumanos
                .stream()
                .filter(humano -> humano.getDocumento(tipo).equals(nro))
                .findFirst();
    }

    @Override
    public boolean existeUsername(String username) {
        return this.colaboradorHumanos
                .stream()
                .anyMatch(humano -> humano.getUsername().equals(username));
    }
}
