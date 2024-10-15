package ar.edu.utn.frba.dds.dtos.heladeras;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class HeladeraOutputDTO {

    public static HeladeraOutputDTO of(Heladera heladera){
        return HeladeraOutputDTO
                .builder()
                .id(heladera.getId())
                .nombre(heladera.getNombre())
                .direccion(heladera.direccionCompleta())
                .capacidadActual(heladera.getCapActual())
                .capacidadMaxima(heladera.getCapacidadMaxima())
                .activa(heladera.getActiva())
                .longitud(heladera.getDireccion().getCoordenadas().getLongitud())
                .latitud(heladera.getDireccion().getCoordenadas().getLatitud())
                .cantViandasRetiradas(heladera.getViandasRetiradas())
                .cantViandasColocadas(heladera.getViandasColocadas())
                .build();
    }

    private Long id;
    private String nombre;
    private String direccion;
    private Integer capacidadActual;
    private Integer capacidadMaxima;
    private Boolean activa;
    private Double longitud;
    private Double latitud;
    private Integer cantViandasRetiradas;
    private Integer cantViandasColocadas;
    private Boolean estaSuscrito;

}
