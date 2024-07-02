package ar.edu.utn.frba.dds.models.entities.comandos;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;

public interface Comando {
    public void ejecutar(Heladera heladera, String mensaje);
}
