package ar.edu.utn.frba.dds.models.entities.comandos;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;

import java.time.LocalDateTime;

public class Alertar implements Comando{

    @Override
    public void ejecutar(Heladera heladera, String mensaje) {
    }
}
