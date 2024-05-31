package ar.edu.utn.frba.dds.colaboraciones;

import java.util.ArrayList;

public class OfertasDisponibles {
    private static ArrayList<Oferta> ofertas = new ArrayList<Oferta>();

    public void agregarOferta(Oferta oferta) {
        ofertas.add(oferta);
    }

    public void canjearOferta(Oferta oferta) {
        oferta.serCanjeada();
        if (oferta.canjesRestantes() == 0) {
            ofertas.remove(oferta);
        }
    }

}
