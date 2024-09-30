package ar.edu.utn.frba.dds.dtos;

import ar.edu.utn.frba.dds.models.entities.personas.Atributo;
import ar.edu.utn.frba.dds.models.entities.personas.TipoAtributo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AtributoOutputDTO {
    private String nombre;
    private String tipo;
    private Boolean obligatorio;

    public static AtributoOutputDTO of(Atributo a){
        return AtributoOutputDTO
                .builder()
                .nombre(a.getNombre())
                .tipo(a.getTipoCampo().toString())
                .obligatorio(a.getTipo() == TipoAtributo.OBLIGATORIO)
                .build();
    }
}
