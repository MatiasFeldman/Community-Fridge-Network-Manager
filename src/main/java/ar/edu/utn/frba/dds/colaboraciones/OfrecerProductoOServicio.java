package ar.edu.utn.frba.dds.colaboraciones;

public class OfrecerProductoOServicio implements ContribucionJuridica{

    private Oferta oferta;
    @Override
    public void contribuir() {
        OfertasDisponibles ofertas = new OfertasDisponibles();
        ofertas.agregarOferta(oferta);
    }

    @Override
    public double calcularPuntaje() {
        return 0;
    }
}
