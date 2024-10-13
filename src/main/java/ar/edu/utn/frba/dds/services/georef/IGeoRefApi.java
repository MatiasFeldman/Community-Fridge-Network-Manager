package ar.edu.utn.frba.dds.services.georef;

import ar.edu.utn.frba.dds.models.entities.ubicacion.GeoRefDeDirecc;

public interface IGeoRefApi {
    GeoRefDeDirecc getCoordYComuna(String direccion, String comuna);
    GeoRefDeDirecc getCoord(String direccion);
    GeoRefDeDirecc hacerRequest(String url);
}
