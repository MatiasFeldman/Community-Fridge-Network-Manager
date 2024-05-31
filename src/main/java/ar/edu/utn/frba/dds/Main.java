package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.ubicacion.RecomendarPuntos;

public class Main {
    public static void main(String[] args) {
        RecomendarPuntos recomendarPuntos = new RecomendarPuntos();

        try {
            recomendarPuntos.solicitarRecomendacionParaHeladera(new Coordenada(-34.58, -58.43), 3.0);
        } catch (Exception e) {
            System.out.println("Error al solicitar recomendacion para heladera: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
