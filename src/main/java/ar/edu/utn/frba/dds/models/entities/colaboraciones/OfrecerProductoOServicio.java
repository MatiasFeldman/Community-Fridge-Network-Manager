package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.repositories.ofertas.imp.OfertasRepository;

public class OfrecerProductoOServicio implements ContribucionJuridica {

    private Oferta oferta;
    private final OfertasRepository ofertasRepository;

    public OfrecerProductoOServicio(Oferta oferta, OfertasRepository ofertasRepository) {
        this.oferta = oferta;
        this.ofertasRepository = ofertasRepository;
    }

    @Override
    public double calcularPuntaje() {
        return 0;
    }
}
