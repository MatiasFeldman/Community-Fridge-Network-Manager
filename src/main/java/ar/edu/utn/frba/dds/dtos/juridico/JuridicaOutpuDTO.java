package ar.edu.utn.frba.dds.dtos.juridico;

import ar.edu.utn.frba.dds.models.entities.personas.Contacto;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class JuridicaOutpuDTO {
    private String razonSocial;
    private String tipo;
    private String rubro;
    private String whatsapp;
    private String telegram;
    private String mail;
    private String direccion;
    private String provincia;

    public static JuridicaOutpuDTO of(Juridica j){
        return JuridicaOutpuDTO
                .builder()
                .razonSocial(j.getRazonSocial())
                .tipo(String.valueOf(j.getTipo()))
                .rubro(j.getRubro())
                .whatsapp(j.tieneMedioDeContacto("WhatsApp") ? j.getMedioDeContacto("WhatsApp") : null)
                .telegram(j.tieneMedioDeContacto("Telegram") ? j.getMedioDeContacto("Telegram") : null)
                .mail(j.tieneMedioDeContacto("Mail") ? j.getMedioDeContacto("Mail") : null)
                .direccion(j.getDireccion().getDireccion())
                .provincia(j.getDireccion().getProvincia().getNombre())
                .build();
    }
}
