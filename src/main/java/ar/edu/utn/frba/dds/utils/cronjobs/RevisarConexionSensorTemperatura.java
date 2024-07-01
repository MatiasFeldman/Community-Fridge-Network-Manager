package ar.edu.utn.frba.dds.utils.cronjobs;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.ReceptorTemperatura;
import ar.edu.utn.frba.dds.models.repositories.receptores_de_temperatura.ReceptoresDeTempRepository;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class RevisarConexionSensorTemperatura implements Runnable {
    private ReceptoresDeTempRepository receptoresTemperatura;

    @Override
    public void run() {
        System.out.println("Revisando conexión...");
        for (ReceptorTemperatura receptorTemperatura : receptoresTemperatura.buscarTodos()){
            receptorTemperatura.evaluarConexion();
        }
    }

}
