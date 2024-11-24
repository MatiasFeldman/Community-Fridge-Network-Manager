package ar.edu.utn.frba.dds.dtos.tecnicos;

import ar.edu.utn.frba.dds.models.entities.personas.Contacto;
import ar.edu.utn.frba.dds.models.entities.tecnicos.AreaCobertura;
import ar.edu.utn.frba.dds.models.entities.tecnicos.Tipo_documento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class TecnicoDTO {
    private String nombre;
    private String apellido;
    private Contacto medioContacto;
    private Tipo_documento tipo;
    private String nroDocumento;
    private String nroCUIL;
    private AreaCobertura areaCobertura;

}
