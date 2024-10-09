package ar.edu.utn.frba.dds.dtos.juridico;

import ar.edu.utn.frba.dds.models.entities.personas.Contacto;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JuridicaOutpuDTO {
    private String razonSocial;
    private String tipo;
    private String rubro;
    private List<Contacto> mediosDeContacto;
    private String direccion;
    private String provincia;

    public static JuridicaOutpuDTO of(Juridica j){
        return JuridicaOutpuDTO
                .builder()
                .razonSocial(j.getRazonSocial())
                .tipo(String.valueOf(j.getTipo()))
                .rubro(j.getRubro())
                .mediosDeContacto(j.getMediosDeContacto())
                .direccion(j.getDireccion().getDireccion())
                .provincia(j.getDireccion().getProvincia().getNombre())
                .build();
    }
}
