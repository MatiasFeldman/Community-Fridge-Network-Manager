package ar.edu.utn.frba.dds.dtos.humanos;

import ar.edu.utn.frba.dds.dtos.atributo_respondido.AtributoRespondidoOutputDTO;
import ar.edu.utn.frba.dds.models.entities.personas.AtributoHumanoRespondido;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.entities.personas.Contacto;
import ar.edu.utn.frba.dds.utils.atributos_faltantes.AtributosFaltantes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class HumanoOutputDTO {
    private List<Contacto> mediosDeContacto;
    private List<AtributoRespondidoOutputDTO> atributos;
    private String direccion;

    public static HumanoOutputDTO of(ColaboradorHumano h) {
        List<AtributoHumanoRespondido> atributosRespondidos = new ArrayList<>(h.getAtributosObligatorios());
        atributosRespondidos.addAll(h.getAtributosOpcionales());

        List<AtributoHumanoRespondido> atributosAMostrar = AtributosFaltantes.todosLosAtributosDe(atributosRespondidos);

        List<AtributoRespondidoOutputDTO> atributos_dto = new ArrayList<>();
        for (AtributoHumanoRespondido a : atributosAMostrar) {
            atributos_dto.add(AtributoRespondidoOutputDTO.of(a));
        }

        return HumanoOutputDTO
                .builder()
                .atributos(atributos_dto)
                .mediosDeContacto(h.getMediosDeContacto())
                .direccion(h.getDireccion().getDireccion())
                .build();

    }
}
