package ar.edu.utn.frba.dds.dtos.juridico;

import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UsuarioJuridicaOutputDTO {
    private Long id;
    private String razonSocial;
    private Double puntos;
    private String foto;

    public static UsuarioJuridicaOutputDTO of(Juridica j) {
        return UsuarioJuridicaOutputDTO.builder()
                .id(j.getId())
                .razonSocial(j.getRazonSocial())
                .puntos(j.getPuntosGanados())
                .foto(j.getUser().getFoto())
                .build();
    }
}
