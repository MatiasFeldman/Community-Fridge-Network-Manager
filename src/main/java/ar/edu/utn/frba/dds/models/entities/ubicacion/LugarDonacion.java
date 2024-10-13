package ar.edu.utn.frba.dds.models.entities.ubicacion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LugarDonacion {
    private String nombre;
    private Coordenada coordenadas;
    private String direccion;
}
