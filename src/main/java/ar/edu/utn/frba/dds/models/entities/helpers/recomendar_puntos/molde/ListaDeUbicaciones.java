package ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos.molde;

import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ListaDeUbicaciones {
    @Getter
    @Setter
    public List<Coordenada> coordenadas ;
}
