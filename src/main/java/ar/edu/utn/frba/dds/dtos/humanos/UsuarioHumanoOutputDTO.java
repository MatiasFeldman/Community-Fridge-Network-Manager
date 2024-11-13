package ar.edu.utn.frba.dds.dtos.humanos;

import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UsuarioHumanoOutputDTO {
    private Long id;
    private String nombre;
    private Double puntos;
    private String foto;

    public static UsuarioHumanoOutputDTO of(ColaboradorHumano h) {
        return UsuarioHumanoOutputDTO.builder()
                .id(h.getId())
                .nombre(h.getAtributosObligatorios().stream().filter(a -> a.getNombreAtributo().equals("Nombre")).findFirst().get().getValor())
                .puntos(h.getPuntosGanados())
                .foto(h.getUser().getFoto())
                .build();
    }

}
