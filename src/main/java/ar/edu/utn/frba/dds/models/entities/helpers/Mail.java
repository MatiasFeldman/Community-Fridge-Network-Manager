package ar.edu.utn.frba.dds.models.entities.helpers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class Mail {
    private String cuerpo;
    private String motivo;
}
