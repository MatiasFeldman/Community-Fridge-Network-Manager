package ar.edu.utn.frba.dds.models.entities.helpers.distancia_entre_coordenadas;

import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;

public class CalculadoraDistancia {
    private static final double EARTH_RADIUS_KM = 6371.0; // Radio de la Tierra en kilómetros

    public static Double calcularDistancia(Coordenada c1, Coordenada c2) {
        // Convertir las coordenadas de grados a radianes
        double lat1Rad = Math.toRadians(c1.getLatitud());
        double lon1Rad = Math.toRadians(c1.getLongitud());
        double lat2Rad = Math.toRadians(c2.getLatitud());
        double lon2Rad = Math.toRadians(c2.getLongitud());

        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        // Aplicar la fórmula Haversine
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Calcular la distancia en kilómetros
        double distanciaKm = EARTH_RADIUS_KM * c;

        // Convertir a metros
        return distanciaKm * 1000;
    }
}
