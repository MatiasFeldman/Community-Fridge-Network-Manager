
package ar.edu.utn.frba.dds.models.main;
import ar.edu.utn.frba.dds.utils.cronjobs.RevisarConexionSensorTemperatura;
import lombok.AllArgsConstructor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class MainRevisarConexion {
    private RevisarConexionSensorTemperatura revisor;

    public void iniciarCronJob(){
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        long intervalo = 5;
        TimeUnit unit = TimeUnit.MINUTES;

        scheduler.scheduleAtFixedRate(revisor, 0, intervalo, unit);
    }

    public static void main(RevisarConexionSensorTemperatura revisor) {
        MainRevisarConexion main = new MainRevisarConexion(revisor);
        main.iniciarCronJob();
    }
}
