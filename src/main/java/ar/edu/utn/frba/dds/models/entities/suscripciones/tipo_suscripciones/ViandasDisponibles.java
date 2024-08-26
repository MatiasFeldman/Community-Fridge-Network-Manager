package ar.edu.utn.frba.dds.models.entities.suscripciones.tipo_suscripciones;

import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.Mensaje;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViandasDisponibles implements Suscripcion {
    public int cantidadViandasDisponibles;
    @Getter
    public final String cuerpo = "La heladera tiene la cantidad de viandas esperadas";
    public String destinatario;

    public static ViandasDisponibles of(String destinatario, int cantidadViandasDisponibles) {
        return ViandasDisponibles
                .builder()
                .destinatario(destinatario)
                .cantidadViandasDisponibles(cantidadViandasDisponibles)
                .build();
    }

    @Override
    public Mensaje getMensaje() {
        return new Mensaje(destinatario, cuerpo);
    }



    @Override
    public boolean verificarCondicion(Integer capacidadActualHeladera, Integer cantidadActual) {
        return cantidadActual == cantidadViandasDisponibles;
    }
}
