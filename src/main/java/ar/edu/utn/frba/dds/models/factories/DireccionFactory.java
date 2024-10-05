package ar.edu.utn.frba.dds.models.factories;

import ar.edu.utn.frba.dds.dtos.direccion.DireccionInputDTO;
import ar.edu.utn.frba.dds.models.entities.ubicacion.GeoRefDeDirecc;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.services.georef.GobiernoAPI;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import ar.edu.utn.frba.dds.utils.direcciones.StringToDireccion;

public class DireccionFactory {
    public static Direccion create(DireccionInputDTO input){
        Direccion direc = StringToDireccion.convertir(input.getCalle());
        GobiernoAPI api = ServiceLocator.instanceOf(GobiernoAPI.class);

        GeoRefDeDirecc georefDirec = api.getCoordYComuna(input.getCalle(), input.getCiudad());
        direc.setCoordenadas(georefDirec.getCoords());
        direc.setComuna(georefDirec.getComuna());
        direc.setProvincia(georefDirec.getProvincia());

        return direc;
    }
}
