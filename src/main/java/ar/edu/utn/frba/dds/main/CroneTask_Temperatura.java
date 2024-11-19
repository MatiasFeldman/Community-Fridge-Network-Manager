package ar.edu.utn.frba.dds.main;

import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class CroneTask_Temperatura implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        HeladerasRepository heladeras = ServiceLocator.instanceOf(HeladerasRepository.class);

        heladeras
                .buscarTodos()
                .forEach(h -> {
                    h.evaluarTemperatura(5.0);
                    heladeras.modificar(h);
                });
    }
}
