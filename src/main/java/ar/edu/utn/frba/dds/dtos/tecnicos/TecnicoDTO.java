package ar.edu.utn.frba.dds.dtos.tecnicos;

import ar.edu.utn.frba.dds.models.entities.personas.Contacto;
import ar.edu.utn.frba.dds.models.entities.tecnicos.AreaCobertura;
import ar.edu.utn.frba.dds.models.entities.tecnicos.TipoTecnico;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class TecnicoDTO {
    private String nombreUsuario;
    private String Contrasenia;
    private String nombre;
    private String apellido;
    private Contacto medioContacto;
    private TipoTecnico tipo;
    private String nroDocumento;
    private String nroCUIL;
    private AreaCobertura areaCobertura;
}
