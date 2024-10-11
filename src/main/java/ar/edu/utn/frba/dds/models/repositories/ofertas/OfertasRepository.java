package ar.edu.utn.frba.dds.models.repositories.ofertas;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Oferta;
import ar.edu.utn.frba.dds.models.repositories.ofertas.dao.OfertasDAO;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
@AllArgsConstructor
public class OfertasRepository {
    private OfertasDAO ofertas;

    public Optional<Oferta> buscarPorId(Long id){return ofertas.buscarPorId(id);}

    public void guardar(Oferta oferta){ofertas.guardar(oferta);}

    public Optional<Oferta> buscarPorNombre(String nombre){return ofertas.buscarPorNombre(nombre);}

    public List<Oferta> buscarPorRubro(String rubro){return ofertas.buscarPorRubro(rubro);}

    public List<Oferta> buscarTodos(){return ofertas.buscarTodos();}

    public void eliminar(Oferta oferta){ofertas.eliminar(oferta);}

    public void modificar(Oferta oferta){ofertas.modficar(oferta);}

    public void canjearOferta(Oferta oferta){ofertas.canjearOferta(oferta);}

}
