package ar.edu.utn.frba.dds.dtos.atributo_respondido;

import ar.edu.utn.frba.dds.models.entities.personas.AtributoHumanoRespondido;
import ar.edu.utn.frba.dds.models.entities.personas.TipoAtributo;
import ar.edu.utn.frba.dds.models.entities.personas.TipoCampoAtributo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AtributoRespondidoOutputDTO {
    private String nombre;
    private String valor;
    private Boolean completado;
    private TipoCampoAtributo tipo;
    private Boolean constante;

    public static AtributoRespondidoOutputDTO of(AtributoHumanoRespondido a, Boolean constante){
        return AtributoRespondidoOutputDTO
                .builder()
                .nombre(a.getNombreAtributo())
                .valor(a.getValor())
                .completado(!a.getValor().isEmpty())
                .tipo(a.getAtributo().getTipoCampo())
                .constante(constante)
                .build();
    }
}
