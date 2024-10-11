package ar.edu.utn.frba.dds.dtos.ofertas;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Oferta;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.Rubro;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class OfertaOutputDTO {
    public static OfertaOutputDTO of(Oferta oferta){
        return OfertaOutputDTO
                .builder()
                .id(oferta.getId())
                .nombre(oferta.getNombre())
                .puntosNecesarios(oferta.getPuntosNecesarios())
                .canjesTotales(oferta.getCanjesTotales())
                .canjesUsados(oferta.getCanjesUsados())
                .image(oferta.getImage())
                .build();
    }

    private Long id;
    private String nombre;
    private Double puntosNecesarios;
    private Rubro rubro;
    private Integer canjesTotales;
    private Integer canjesUsados;
    private String image;
}
