package ar.edu.utn.frba.dds.dtos.humanos;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Contribucion;
import ar.edu.utn.frba.dds.models.entities.personas.AtributoHumanoRespondido;
import ar.edu.utn.frba.dds.models.entities.personas.Contacto;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.repositories.ofertas.imp.OfertasRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;

@Getter
@AllArgsConstructor
public class HumanoInputDTO {
    private ArrayList<AtributoHumanoRespondido> atributosObligatorios;
    private ArrayList<Contacto> mediosDeContacto;
    private ArrayList<AtributoHumanoRespondido> atributosOpcionales;
    private ArrayList<Contribucion> contribuciones;
    private OfertasRepository ofertasDisponibles;
    private Usuario user;

}
