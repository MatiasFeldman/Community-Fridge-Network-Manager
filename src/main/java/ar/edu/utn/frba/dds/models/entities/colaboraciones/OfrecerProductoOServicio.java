package ar.edu.utn.frba.dds.models.entities.colaboraciones;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OfrecerProductoOServicio implements ContribucionJuridica {

    private Oferta oferta;

    public static OfrecerProductoOServicio of(Oferta oferta) {
        return new OfrecerProductoOServicio(oferta);
    }

    @Override
    public double calcularPuntaje() {
        return 0;
    }
}
