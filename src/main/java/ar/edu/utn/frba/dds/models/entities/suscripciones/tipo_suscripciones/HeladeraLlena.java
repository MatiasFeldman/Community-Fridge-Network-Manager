package ar.edu.utn.frba.dds.models.entities.suscripciones.tipo_suscripciones;


import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.Mensaje;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Objects;

@SuperBuilder
@AllArgsConstructor
@Entity
@DiscriminatorValue("heladera_llena")
public class HeladeraLlena extends Suscripcion {

    public static HeladeraLlena of(String destinatario, int cantidadViandasFaltantes) {
        return HeladeraLlena
                .builder()
                .destinatario(destinatario)
                .cuerpo("La heladera tiene la cantidad de viandas esperadas")
                .cantidad(cantidadViandasFaltantes)
                .build();
    }

    @Override
    public Boolean verificarCondicion(Integer capActual, Integer cantActual) {
        return Objects.equals(super.cantidad, capActual);
    }
}
