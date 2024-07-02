package ar.edu.utn.frba.dds.models.entities.comandos;

public class ActivarAlarma implements Comando{
    @Override
    public void ejecutar() {
        System.out.println("Alarma activada");
    }
}
