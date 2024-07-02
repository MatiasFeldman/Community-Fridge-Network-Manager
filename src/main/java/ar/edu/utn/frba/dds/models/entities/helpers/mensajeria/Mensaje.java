package ar.edu.utn.frba.dds.models.entities.helpers.mensajeria;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class Mensaje {
    public String destinatario;
    public String cuerpo;
}
