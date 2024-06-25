package ar.edu.utn.frba.dds.models.entities.comandos;

public class Alertar implements Comando{

    @Override
    public void ejecutar() {
        System.out.println("Alerta generada");
    }
}
