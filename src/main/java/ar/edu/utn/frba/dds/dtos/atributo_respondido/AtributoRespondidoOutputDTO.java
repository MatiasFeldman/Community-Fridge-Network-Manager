package ar.edu.utn.frba.dds.dtos.atributo_respondido;

import ar.edu.utn.frba.dds.models.entities.personas.AtributoHumanoRespondido;
import ar.edu.utn.frba.dds.models.entities.personas.TipoAtributo;
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
    private TipoAtributo tipo;

    public static AtributoRespondidoOutputDTO of(AtributoHumanoRespondido a){
        System.out.println(a.getNombreAtributo());
        System.out.println(a.getValor());
        System.out.println(a.getValor().isEmpty());
        System.out.println("--------------------");
        return AtributoRespondidoOutputDTO
                .builder()
                .nombre(a.getNombreAtributo())
                .valor(a.getValor())
                .completado(!a.getValor().isEmpty())
                .tipo(a.getAtributo().getTipo())
                .build();
    }
}
