package ar.edu.utn.frba.dds.colaboraciones;

public class OfrecerProductoOServicio implements ContribucionJuridica{

    private Oferta oferta;
    @Override
    public void contribuir(ColaboracionesRealizadas colaboracionesRealizadas) {
        OfertasDisponibles ofertas = new OfertasDisponibles();
        ofertas.agregarOferta(oferta);
    }
}
