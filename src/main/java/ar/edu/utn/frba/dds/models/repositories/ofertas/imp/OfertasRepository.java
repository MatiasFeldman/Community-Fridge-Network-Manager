package ar.edu.utn.frba.dds.models.repositories.ofertas.imp;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Oferta;
import ar.edu.utn.frba.dds.models.repositories.IOfertasRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OfertasRepository implements IOfertasRepository {
    private List<Oferta> ofertas;

    public OfertasRepository() {
        this.ofertas = new ArrayList<>();
    }

    @Override
    public void guardar(Oferta oferta) {
        ofertas.add(oferta);
    }

    @Override
    public Optional<Oferta> buscarPorNombre(String nombre) {
        return ofertas.stream().filter(oferta -> oferta.getNombre().equals(nombre)).findFirst();
    }

    @Override
    public Optional<Oferta> buscarPorRubro(String rubro) {
        return ofertas.stream().filter(oferta -> oferta.getRubro().getNombre().equals(rubro)).findFirst();
    }

    @Override
    public List<Oferta> buscarTodos() {
        return ofertas;
    }

    @Override
    public void eliminar(Oferta oferta) {
        ofertas.remove(oferta);
    }

    public void canjearOferta(Oferta oferta) {
        oferta.serCanjeada();
        if (oferta.canjesRestantes() == 0) {
            this.eliminar(oferta);
        }
    }
}
