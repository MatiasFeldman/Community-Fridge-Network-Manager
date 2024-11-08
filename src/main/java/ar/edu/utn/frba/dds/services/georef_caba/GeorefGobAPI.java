package ar.edu.utn.frba.dds.services.georef_caba;

import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;

public interface GeorefGobAPI {
    String getDirecc(Coordenada cord);
    String hacerRequest(String url);

}
