package ar.edu.utn.frba.dds.models.factories.tecnicos;

import ar.edu.utn.frba.dds.dtos.tecnicos.TecnicoDTO;
import ar.edu.utn.frba.dds.models.entities.tecnicos.Tecnico;

public class TecnicosFactory {
    public static Object crear(Object solicitud){
        TecnicoDTO dto = (TecnicoDTO) solicitud;

        return Tecnico.create(dto);

    }

}
