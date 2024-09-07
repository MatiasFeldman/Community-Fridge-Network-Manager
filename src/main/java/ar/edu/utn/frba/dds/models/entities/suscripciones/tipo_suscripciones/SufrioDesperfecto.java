package ar.edu.utn.frba.dds.models.entities.suscripciones.tipo_suscripciones;

import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.Mensaje;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@SuperBuilder
@Entity
@DiscriminatorValue("sufrio_desperfecto")
public class SufrioDesperfecto extends Suscripcion {

    public static SufrioDesperfecto of(String destinatario) {
        return SufrioDesperfecto
                .builder()
                .destinatario(destinatario)
                .cuerpo("La heladera sufrió un desperfecto")
                .build();
    }

    @Override
    public Boolean verificarCondicion(Integer capActual, Integer cantActual) {
        return capActual == -1 || cantActual == -1;
    }
}