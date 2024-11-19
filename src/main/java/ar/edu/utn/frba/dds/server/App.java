package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.main.CroneTask_Conexion;
import ar.edu.utn.frba.dds.main.CroneTask_Reportes;
import ar.edu.utn.frba.dds.main.CroneTask_Temperatura;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class App {

    public static void main(String[] args) {
        Server.init();
        startScheduler();
    }

    private static void startScheduler(){
        try {
            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler scheduler = sf.getScheduler();

            JobDetail job1 = JobBuilder.newJob(CroneTask_Reportes.class)
                    .withIdentity("reportesJob", "grupo1")
                    .usingJobData("Info", "Valor")
                    .build();

            JobDetail job2 = JobBuilder.newJob(CroneTask_Conexion.class)
                    .withIdentity("conexionJob", "grupo1")
                    .usingJobData("Info", "Valor")
                    .build();

            JobDetail job3 = JobBuilder.newJob(CroneTask_Temperatura.class)
                    .withIdentity("temperaturaJob", "grupo1")
                    .usingJobData("Info", "Valor")
                    .build();

            Trigger triggerReportes = TriggerBuilder.newTrigger()
                    .withIdentity("reportesTrigger", "grupo1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInHours(168)
                            .repeatForever())
                    .build();

            Trigger triggerConexion = TriggerBuilder.newTrigger()
                    .withIdentity("conexionTrigger", "grupo1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInMinutes(5)
                            .repeatForever())
                    .build();

            Trigger triggerTemperatura = TriggerBuilder.newTrigger()
                    .withIdentity("temperaturaTrigger", "grupo1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInMinutes(4)
                            .repeatForever())
                    .build();

            scheduler.scheduleJob(job1, triggerReportes);
            scheduler.scheduleJob(job2, triggerConexion);
            scheduler.scheduleJob(job3, triggerTemperatura);


            scheduler.start();


        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
