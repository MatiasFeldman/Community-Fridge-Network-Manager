package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.adapters.APIAdapter;
import ar.edu.utn.frba.dds.ubicacion.Coordenada;

public class Main {
    public static void main(String[] args) {
        APIAdapter api = new APIAdapter();

        try {
            api.solicitarRecomendacionParaHeladera(new Coordenada(-34.58, -58.43), 3.0);
        } catch (Exception e) {
            System.out.println("Error al solicitar recomendacion para heladera: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
