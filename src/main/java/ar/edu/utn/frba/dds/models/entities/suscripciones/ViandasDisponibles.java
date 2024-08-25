package ar.edu.utn.frba.dds.models.entities.suscripciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.Mensaje;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ViandasDisponibles implements MotivoNotificacion {
    public int cantidadViandasDisponibles;
    @Getter
    public final String cuerpo = "La heladera tiene la cantidad de viandas esperadas";
    public final String destinatario;

    @Override
    public boolean validar(Heladera heladera) {
        return heladera.getCantActual() == cantidadViandasDisponibles;
    }

    @Override
    public Mensaje getMensaje() {
        return new Mensaje(destinatario, cuerpo);
    }
}
