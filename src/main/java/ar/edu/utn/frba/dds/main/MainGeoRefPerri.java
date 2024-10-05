package ar.edu.utn.frba.dds.main;

import ar.edu.utn.frba.dds.models.entities.ubicacion.GeoRefDeDirecc;
import ar.edu.utn.frba.dds.services.georef.GobiernoAPI;

public class MainGeoRefPerri {
    public static void main(String[] args) {
        GobiernoAPI api = new GobiernoAPI();
        GeoRefDeDirecc coordYComuna = api.getCoordYComuna("Yatay 50", "CABA");
        System.out.println(coordYComuna.getComuna().getNombre());
        System.out.println(coordYComuna.getCoords().getLatitud());
        System.out.println(coordYComuna.getCoords().getLongitud());
        System.out.println(coordYComuna.getProvincia().getNombre());
    }
}
