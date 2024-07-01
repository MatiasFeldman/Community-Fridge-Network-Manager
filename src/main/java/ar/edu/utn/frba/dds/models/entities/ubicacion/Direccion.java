package ar.edu.utn.frba.dds.models.entities.ubicacion;

import lombok.Builder;

@Builder
public class Direccion {
    private Calle calle;
    private int altura;
    private Coordenada coordenadas;

    public static Direccion of(DireccionDTO dto){
        DireccionBuilder builder = Direccion
                .builder()
                .calle(dto.getCalle())
                .altura(dto.getAltura());
        //TODO: calcular coordenadas
        return builder.build();
    }

    public String direccionCompleta() {
        return calle.getNombre() + " " + altura;
    }
}
