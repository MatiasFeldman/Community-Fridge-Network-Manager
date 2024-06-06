package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.entities.helpers.ConexionAPI;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.models.entities.ubicacion.RecomendarPuntos;

public class Main {
    public static void main(String[] args) {
        ConexionAPI api = new ConexionAPI();
        RecomendarPuntos recomendarPuntos = new RecomendarPuntos(api);

        try {
            recomendarPuntos.solicitarRecomendacionParaHeladera(new Coordenada(-34.58, -58.43), 3.0);
        } catch (Exception e) {
            System.out.println("Error al solicitar recomendacion para heladera: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
