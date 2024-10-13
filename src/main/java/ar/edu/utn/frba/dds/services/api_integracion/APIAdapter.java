package ar.edu.utn.frba.dds.services.api_integracion;

import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.models.entities.ubicacion.LugarDonacion;

import java.util.List;

public interface APIAdapter {
    List<LugarDonacion> getLugaresCercanos(Coordenada coordenadas);
}
