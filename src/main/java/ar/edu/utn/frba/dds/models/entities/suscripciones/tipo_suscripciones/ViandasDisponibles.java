package ar.edu.utn.frba.dds.models.entities.suscripciones.tipo_suscripciones;

import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Objects;

@AllArgsConstructor
@SuperBuilder
@Entity
@DiscriminatorValue("heladera_llena")
public class ViandasDisponibles extends Suscripcion {
    @Column(name="cantidad_de_viandas_disponibles")

    public static ViandasDisponibles of(String destinatario, int cantidadViandasDisponibles) {
        return ViandasDisponibles
                .builder()
                .destinatario(destinatario)
                .cuerpo("La heladera tiene la cantidad de viandas esperadas")
                .cantidad(cantidadViandasDisponibles)
                .build();
    }

    @Override
    public Boolean verificarCondicion(Integer capacidadActualHeladera, Integer cantidadActual) {
        return Objects.equals(cantidadActual, super.cantidad);
    }
}
