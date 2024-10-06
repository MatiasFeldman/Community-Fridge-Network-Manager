package ar.edu.utn.frba.dds.models.factories.direcciones;

import ar.edu.utn.frba.dds.dtos.direccion.DireccionInputDTO;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;

public class DireccionFactory {
    public static Direccion create(DireccionInputDTO input){
        return Direccion.of(input.getCalle(), input.getCiudad());
    }
}
