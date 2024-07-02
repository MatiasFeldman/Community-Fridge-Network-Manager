package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SensorTemperatura {
    private ReceptorTemperatura receptorTemperatura;

    public void recibirTemperatura(String temperatura){
        double temp = Double.parseDouble(temperatura);
        //Simula recibir la temperatura de la heladera
        // Enviar temperatura a heladera
    }
}
