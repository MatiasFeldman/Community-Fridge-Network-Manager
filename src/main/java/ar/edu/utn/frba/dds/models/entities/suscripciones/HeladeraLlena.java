package ar.edu.utn.frba.dds.models.entities.suscripciones;


import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import lombok.Getter;

public class HeladeraLlena {
    // Faltan n viandas para que la heladera esté llena y no se puedan ingresar más viandas. Un colaborador distribuidor puede llevar N viandas a otra heladera que está menos llena.

    public int cantidadViandasFaltantes;
    @Getter
    public final String mensaje = "La heladera tiene la cantidad de viandas esperadas";

    public boolean validar(Heladera heladera) {
        return heladera.getCapacidadActual() == cantidadViandasFaltantes;
    }
}
