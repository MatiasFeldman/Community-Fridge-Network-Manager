package ar.edu.utn.frba.dds.main;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class CroneTask_Conexion implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        HeladerasRepository heladeras = ServiceLocator.instanceOf(HeladerasRepository.class);

        heladeras
                .buscarTodos()
                .forEach(h -> {
                    h.evaluarConexion();
                    heladeras.modificar(h);
                });
    }
}
