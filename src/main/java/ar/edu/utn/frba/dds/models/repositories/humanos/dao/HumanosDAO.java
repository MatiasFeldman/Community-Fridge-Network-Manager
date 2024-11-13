package ar.edu.utn.frba.dds.models.repositories.humanos.dao;

import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;

import java.util.List;
import java.util.Optional;

public interface HumanosDAO {
    public void guardar(ColaboradorHumano colaboradorHumano);

    public List<ColaboradorHumano> buscarTodos();

    public Optional<ColaboradorHumano> buscarPorIdUsuario(Long id);

    public void eliminar(ColaboradorHumano colaboradorHumano);

    void modificar(ColaboradorHumano colaboradorHumano);

    public Optional<ColaboradorHumano> buscarPorDocumento(String tipo, String nro);

    public boolean existeUsername(String username);

    public Optional<ColaboradorHumano> buscarPorTarjeta(Long id);

}
