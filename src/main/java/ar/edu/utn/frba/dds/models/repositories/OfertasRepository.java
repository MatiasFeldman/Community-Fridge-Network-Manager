package ar.edu.utn.frba.dds.models.repositories;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Oferta;

import java.util.List;
import java.util.Optional;

public interface OfertasRepository {
    public void guardar(Oferta oferta);

    public Optional<Oferta> buscarPorNombre(String nombre);

    public Optional<Oferta> buscarPorRubro(String rubro);

    public List<Oferta> buscarTodos();

    public void eliminar(Oferta oferta);
}
