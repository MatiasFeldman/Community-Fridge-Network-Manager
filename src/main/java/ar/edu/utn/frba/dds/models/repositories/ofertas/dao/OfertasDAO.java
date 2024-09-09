package ar.edu.utn.frba.dds.models.repositories.ofertas.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Oferta;

import java.util.List;
import java.util.Optional;

public interface OfertasDAO {
    public void guardar(Oferta oferta);

    public Optional<Oferta> buscarPorNombre(String nombre);

    public void modficar(Oferta oferta);

    public List<Oferta> buscarPorRubro(String rubro);

    public List<Oferta> buscarTodos();

    public void eliminar(Oferta oferta);

    public Optional<Oferta> buscarPorId(Long id);
}
