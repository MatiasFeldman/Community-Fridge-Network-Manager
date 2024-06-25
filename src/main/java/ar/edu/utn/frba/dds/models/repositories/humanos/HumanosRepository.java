package ar.edu.utn.frba.dds.models.repositories.humanos;

import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.repositories.humanos.dao.HumanosDAO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class HumanosRepository {
    private HumanosDAO humanos;

    public HumanosRepository(HumanosDAO humanos) {
        this.humanos = humanos;
    }

    public void guardar(Humano humano) {
        humanos.guardar(humano);
    }

    public Optional<Humano> buscarPorUUID(UUID uuid) {
        return humanos.buscarPorId(uuid);
    }

    public List<Humano> buscarTodos() {
        return humanos.buscarTodos();
    }

    public void eliminar(Humano humano) {
        humanos.eliminar(humano);
    }
}
