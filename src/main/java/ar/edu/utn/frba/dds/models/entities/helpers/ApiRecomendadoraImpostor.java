// ApiRecomendadoraImpostor.java
package ar.edu.utn.frba.dds.models.entities.helpers;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Setter
public class ApiRecomendadoraImpostor implements IRecomendadoraDePuntosAPI {
    private double lat;
    private double lon;
    private double rad;

    @Override
    public void get() {
        // Simula una llamada API
    }

    @Override
    public String revisarRespuesta() {
        System.out.println("Respuesta del API: ");
        return "[{\"latitud\":40.712776,\"longitud\":-74.005974},{\"latitud\":34.052235,\"longitud\":-118.243683}]";
    }
}
