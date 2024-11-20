package ar.edu.utn.frba.dds.dtos.suscripcion;

import ar.edu.utn.frba.dds.models.entities.suscripciones.SuscripcionAHeladera;
import ar.edu.utn.frba.dds.models.entities.suscripciones.tipo_suscripciones.HeladeraLlena;
import ar.edu.utn.frba.dds.models.entities.suscripciones.tipo_suscripciones.SufrioDesperfecto;
import ar.edu.utn.frba.dds.models.entities.suscripciones.tipo_suscripciones.ViandasDisponibles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SuscripcionOutputDTO {
    private Long idSuscripcion;
    private String tipo;
    private Integer cantidad;
    private String nombreHeladera;
    private Long idHeladera;

    public static SuscripcionOutputDTO of(SuscripcionAHeladera suscripcion) {
        SuscripcionOutputDTOBuilder s = SuscripcionOutputDTO
                .builder()
                .idSuscripcion(suscripcion.getId())
                .cantidad(suscripcion.getSuscripcion().getCantidad())
                .nombreHeladera(suscripcion.getHeladera().getNombre())
                .idHeladera(suscripcion.getHeladera().getId());

        if (suscripcion.getSuscripcion() instanceof ViandasDisponibles) {
            s.tipo("Viandas Disponibles");
        } else if (suscripcion.getSuscripcion() instanceof SufrioDesperfecto) {
            s.tipo("Sufrió Desperfecto");
            s.cantidad(null);
        } else if (suscripcion.getSuscripcion() instanceof HeladeraLlena) {
            s.tipo("Espacio Disponible");
        } else {
            s.tipo("Tipo de suscripción desconocido");
        }
        return s.build();
    }
}
