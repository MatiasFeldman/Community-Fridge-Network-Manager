package ar.edu.utn.frba.dds.dtos.humanos;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Contribucion;
import ar.edu.utn.frba.dds.models.entities.personas.AtributoHumanoRespondido;
import ar.edu.utn.frba.dds.models.entities.personas.Contacto;
import ar.edu.utn.frba.dds.models.entities.personas.TipoAtributo;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.repositories.ofertas.dao.OfertasCollection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class HumanoInputDTO {
    private ArrayList<AtributoHumanoRespondido> atributosObligatorios;
    private ArrayList<Contacto> mediosDeContacto;
    private ArrayList<AtributoHumanoRespondido> atributosOpcionales;
    private Usuario user;
    private Direccion direccion;

    public static HumanoInputDTO create(String username, String password, AtributoHumanoRespondido ... atributos){
        HumanoInputDTO dto = new HumanoInputDTO();
        dto.atributosObligatorios = new ArrayList<>();
        dto.atributosOpcionales = new ArrayList<>();
        dto.mediosDeContacto = new ArrayList<>();
        dto.direccion = null;
        dto.user = new Usuario(username, password);

        for (AtributoHumanoRespondido atributo : atributos){
            if(atributo.getNombreAtributo().equalsIgnoreCase("whatsapp")){
                Contacto contacto = Contacto.of("Whatsapp", atributo.getValor());
                dto.mediosDeContacto.add(contacto);
            } else if (atributo.getNombreAtributo().equalsIgnoreCase("mail")){
                Contacto contacto = Contacto.of("Mail", atributo.getValor());
                dto.mediosDeContacto.add(contacto);
            } else if (atributo.getNombreAtributo().equalsIgnoreCase("telegram")){
                Contacto contacto = Contacto.of("Telegram", atributo.getValor());
                dto.mediosDeContacto.add(contacto);
            } else{
                if (atributo.getAtributo().getTipo() == TipoAtributo.OBLIGATORIO){
                    dto.atributosObligatorios.add(atributo);
                } else {
                    dto.atributosOpcionales.add(atributo);
                }
            }
        }
        return dto;
    }
}
