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

        List<AtributoHumanoRespondido> atributosAMostrar = h.getAtributosIncompletos();

        List<AtributoRespondidoOutputDTO> atributos_dto = new ArrayList<>();
        for (AtributoHumanoRespondido a : atributosAMostrar) {
            switch (a.getNombreAtributo()) {
                case "Direccion":
                    if (!(h.getDireccion() == null)) {
                        a.setValor(h.getDireccion().getDireccion());
                    } else{
                        a.setValor("");
                    }
                    atributos_dto.add(AtributoRespondidoOutputDTO.of(a, false));
                    break;
                case "Provincia":
                    if (!(h.getDireccion() == null)){
                    a.setValor(String.valueOf(h.getDireccion().getProvincia().getNombre()));
                    }
                    else{
                        a.setValor("");
                    }
                    atributos_dto.add(AtributoRespondidoOutputDTO.of(a, false));
                    break;
                    /*
                case "WhatsApp":
                    if (h.tieneMedioDeContacto("WhatsApp")) {
                        a.setValor(h.getMedioDeContacto("WhatsApp"));
                    }
                    atributos_dto.add(AtributoRespondidoOutputDTO.of(a, false));
                    break;
                case "Telegram":
                    if (h.tieneMedioDeContacto("Telegram")) {
                        a.setValor(h.getMedioDeContacto("Telegram"));
                    }
                    atributos_dto.add(AtributoRespondidoOutputDTO.of(a, false));
                    break;
                case "Mail":
                    if (h.tieneMedioDeContacto("Mail")) {
                        a.setValor(h.getMedioDeContacto("Mail"));
                    }
                    atributos_dto.add(AtributoRespondidoOutputDTO.of(a, false));
                    break;
                    */
                default:
                    atributos_dto.add(AtributoRespondidoOutputDTO.of(a, true));
            }

        }

        return HumanoOutputDTO
                .builder()
                .atributos(atributos_dto)
                .direccion(h.getDireccion() == null ? "" : h.getDireccion().getDireccion())
                .build();

    }
}
