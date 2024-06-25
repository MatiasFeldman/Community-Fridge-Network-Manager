package ar.edu.utn.frba.dds.models.entities.suscripciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ViandasDisponibles implements MotivoNotificion {
    public int cantidadViandasDisponibles;
    @Getter
    public final String mensaje = "La heladera tiene la cantidad de viandas esperadas";

    public boolean validar(Heladera heladera) {
        return heladera.getCapacidadActual() == cantidadViandasDisponibles;
    }
}