package ar.edu.utn.frba.dds.models.entities.tecnicos;

import ar.edu.utn.frba.dds.dtos.tecnicos.TecnicoDTO;
import ar.edu.utn.frba.dds.models.entities.personas.Contacto;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class Tecnico {
    private String nombre;
    private String apellido;
    private Contacto medioContacto;
    private TipoTecnico tipo;
    private String nroDocumento;
    private String nroCUIL;
    private AreaCobertura areaCobertura;

    public static Tecnico create(TecnicoDTO dto) {
        return Tecnico.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .medioContacto(dto.getMedioContacto())
                .tipo(dto.getTipo())
                .nroDocumento(dto.getNroDocumento())
                .nroCUIL(dto.getNroCUIL())
                .areaCobertura(dto.getAreaCobertura())
                .build();
    }

    public boolean puedeIrA(Direccion direccion){
        return areaCobertura.seEncuentraEnRango(direccion);
    }

    public Double distanciaA(Direccion direccion){
        return areaCobertura.distanciaA(direccion);
    }
}
