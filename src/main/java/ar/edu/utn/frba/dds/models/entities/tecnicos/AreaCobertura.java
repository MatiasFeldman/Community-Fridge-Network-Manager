package ar.edu.utn.frba.dds.models.entities.tecnicos;

import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;

public class AreaCobertura {
    private Direccion Direccion;
    private int maxDistanciaAComunas;

    public boolean seEncuentraEnRango(int comuna) {
        int comuna_minima = Direccion.getComuna() - maxDistanciaAComunas;
        int comuna_maxima = Direccion.getComuna() + maxDistanciaAComunas;
        return comuna >= comuna_minima && comuna <= comuna_maxima;
    }
}
