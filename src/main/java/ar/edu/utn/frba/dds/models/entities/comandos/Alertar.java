package ar.edu.utn.frba.dds.models.entities.comandos;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.repositories.alertas.AlertasRepository;

import java.time.LocalDateTime;

public class Alertar implements Comando{
    private AlertasRepository alertasRepository;

    @Override
    public void ejecutar(Heladera heladera, String mensaje) {
        alertasRepository.guardar(new Alerta(mensaje, heladera, LocalDateTime.now()));
    }
}
