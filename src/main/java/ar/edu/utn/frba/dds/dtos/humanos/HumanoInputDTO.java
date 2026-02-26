package ar.edu.utn.frba.dds.dtos.humanos;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Canjes;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.Contribucion;
import ar.edu.utn.frba.dds.models.entities.personas.AtributoHumanoRespondido;
import ar.edu.utn.frba.dds.models.entities.personas.Contacto;
import ar.edu.utn.frba.dds.models.entities.personas.TipoAtributo;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.models.entities.usuarios.TipoRol;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.factories.sending_strategy.SendingStrategyFactory;
import ar.edu.utn.frba.dds.models.repositories.ofertas.dao.OfertasCollection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class HumanoInputDTO {
    private List<AtributoHumanoRespondido> atributosObligatorios;
    private List<String> nombresMediosDeContacto;
    private List<AtributoHumanoRespondido> atributosOpcionales;
    private List<Canjes> canjesRealizados;
    private Usuario user;
    private Direccion direccion;

    public static HumanoInputDTO create(String username, String password, AtributoHumanoRespondido... atributos) {
        HumanoInputDTO dto = new HumanoInputDTO();
        dto.atributosObligatorios = new ArrayList<>();
        dto.atributosOpcionales = new ArrayList<>();
        dto.canjesRealizados = new ArrayList<>();
        dto.nombresMediosDeContacto = new ArrayList<>();
        dto.nombresMediosDeContacto.add("Mail");
        dto.nombresMediosDeContacto.add("WhatsApp");
        dto.nombresMediosDeContacto.add("Telegram");
        dto.direccion = null;
        dto.user = new Usuario(username, password, List.of(TipoRol.HUMANO));

        for (AtributoHumanoRespondido atributo : atributos) {
                if (atributo.getAtributo().getTipo() == TipoAtributo.OBLIGATORIO) {
                    dto.atributosObligatorios.add(atributo);
                } else {
                    dto.atributosOpcionales.add(atributo);
                }
                // si alguno de los atributos tiene tipo 'whatsapp', 'mail' o 'telegram' se agrega a los medios de contacto
                switch (atributo.getAtributo().getNombre()) {
                    case "Whatsapp":
                        dto.user.setStrategiaDeEnvio(SendingStrategyFactory.create("WHATSAPP"));
                        break;
                    case "Mail":
                        dto.user.setStrategiaDeEnvio(SendingStrategyFactory.create("EMAIL"));
                        break;
                    case "Telegram":
                        dto.user.setStrategiaDeEnvio(SendingStrategyFactory.create("TELEGRAM"));
                        break;
                }
        }
        return dto;
    }
}
