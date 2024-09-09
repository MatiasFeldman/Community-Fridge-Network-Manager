package ar.edu.utn.frba.dds.models.repositories.ofertas.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Oferta;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OfertasCollection implements OfertasDAO {
    private List<Oferta> ofertas;

    public OfertasCollection() {
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
    public List<Oferta> buscarPorRubro(String rubro) {
        return ofertas.stream().filter(oferta -> oferta.getRubro().getNombre().equals(rubro)).toList();
    }

    @Override
    public void modficar(Oferta oferta){
        Optional<Oferta> ofertaOptional = this.buscarPorId(oferta.getId());
        ofertaOptional.ifPresent(oferta1 -> {
            this.ofertas.remove(oferta1);
            this.ofertas.add(oferta);
        });
    }


    @Override
    public List<Oferta> buscarTodos() {
        return ofertas;
    }

    @Override
    public void eliminar(Oferta oferta) {
        ofertas.remove(oferta);
    }

    @Override
    public Optional<Oferta> buscarPorId(Long id) {
        return ofertas
                .stream()
                .filter(o -> o.getId().equals(id))
                .findFirst();
    }

    public void canjearOferta(Oferta oferta) {
        oferta.serCanjeada();
        if (oferta.canjesRestantes() == 0) {
            this.eliminar(oferta);
        }
    }
}
