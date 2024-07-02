package ar.edu.utn.frba.dds.models.entities.ubicacion;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Direccion {
    private Calle calle;
    private int altura;
    private Coordenada coordenadas;
    private int comuna;

    public static Direccion of(DireccionDTO dto){
        return Direccion
                .builder()
                .calle(dto.getCalle())
                .altura(dto.getAltura())
                .comuna(dto.getComuna())
                .build();
    }

    public String direccionCompleta() {
        return calle.getNombre() + " " + altura;

    public boolean esCercaDe(Direccion direccion) {
        return this.calle.equals(direccion.calle) && Math.abs(this.altura - direccion.altura) < 10;
    }
}
