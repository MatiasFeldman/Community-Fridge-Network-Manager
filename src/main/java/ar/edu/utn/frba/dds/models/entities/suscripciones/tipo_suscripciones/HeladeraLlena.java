package ar.edu.utn.frba.dds.models.entities.suscripciones.tipo_suscripciones;


import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.Mensaje;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class HeladeraLlena implements Suscripcion {
    private int cantidadViandasFaltantes;

    private final String mensaje = "La heladera tiene la cantidad de viandas esperadas";
    private String destinatario;

    public static HeladeraLlena of(String destinatario, int cantidadViandasFaltantes) {
        return HeladeraLlena
                .builder()
                .destinatario(destinatario)
                .cantidadViandasFaltantes(cantidadViandasFaltantes)
                .build();
    }

    @Override
    public Mensaje getMensaje() {
        return new Mensaje(mensaje, destinatario);
    }

    @Override
    public boolean verificarCondicion(Integer capActual, Integer cantActual) {
        return cantidadViandasFaltantes == capActual;
    }
}
