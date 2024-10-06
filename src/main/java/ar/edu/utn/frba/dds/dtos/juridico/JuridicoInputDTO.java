package ar.edu.utn.frba.dds.dtos.juridico;

import ar.edu.utn.frba.dds.dtos.direccion.DireccionInputDTO;
import ar.edu.utn.frba.dds.models.entities.helpers.recomendar_puntos.APIRecomendadoraDePuntos;
import ar.edu.utn.frba.dds.models.entities.personas.AtributoHumanoRespondido;
import ar.edu.utn.frba.dds.models.entities.personas.Contacto;
import ar.edu.utn.frba.dds.models.entities.personas.Tipo;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.models.entities.ubicacion.RecomendarPuntos;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.factories.direcciones.DireccionFactory;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class JuridicoInputDTO {
    private Usuario user;
    private String razonSocial;
    private Tipo tipo;
    private String rubro;
    private List<Contacto> mediosDeContacto;
    private Direccion direccion;
    private String provincia;
    private RecomendarPuntos recomendador;


    public JuridicoInputDTO(String username,String password, String razonSocial, String tipo, String rubro, List<Contacto> mediosDeContacto, String direccion,String provincia) {
        this.user = new Usuario(username, password);
        this.razonSocial = razonSocial;
        this.tipo = Tipo.valueOf(tipo);
        this.rubro = rubro;
        this.mediosDeContacto = mediosDeContacto;
        if (direccion != null && provincia != null) {
            this.direccion = DireccionFactory.create(new DireccionInputDTO(direccion, provincia));
        }
        this.provincia = provincia;
        this.recomendador = new RecomendarPuntos();
    }
}
