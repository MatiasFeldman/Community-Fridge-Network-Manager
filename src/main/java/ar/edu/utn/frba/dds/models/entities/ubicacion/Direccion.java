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

    public static Direccion of(String calle, int altura){
        return Direccion
                .builder()
                .calle(new Calle(calle))
                .altura(altura)
                .build();
    }

    public String direccionCompleta() {
        return calle.getNombre() + " " + altura;
    }

    public boolean esCercaDe(Direccion dire) {
        return (this.calle.equals(dire.getCalle()) && Math.abs(this.altura - dire.getAltura()) < 10);
    }

}

