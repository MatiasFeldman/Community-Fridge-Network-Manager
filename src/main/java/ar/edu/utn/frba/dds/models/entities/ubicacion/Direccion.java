package ar.edu.utn.frba.dds.models.entities.ubicacion;

public class Direccion {
    private Calle calle;
    private int altura;

    public boolean esCercaDe(Direccion direccion) {
        return this.calle.equals(direccion.calle) && Math.abs(this.altura - direccion.altura) < 10;
    }
}
