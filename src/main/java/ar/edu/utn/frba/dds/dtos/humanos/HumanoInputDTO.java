package ar.edu.utn.frba.dds.dtos.humanos;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.ContribucionHumana;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaHumano;
import ar.edu.utn.frba.dds.models.entities.personas.AtributoHumano;
import ar.edu.utn.frba.dds.models.entities.personas.Contacto;
import ar.edu.utn.frba.dds.models.repositories.ofertas.imp.OfertasRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class HumanoInputDTO {
    private ArrayList<AtributoHumano> atributosObligatorios;
    private ArrayList<Contacto> mediosDeContacto;
    private ArrayList<AtributoHumano> atributosOpcionales;
    private double puntosCanjeados;
    private ArrayList<ContribucionHumana> contribuciones;
    private OfertasRepository ofertasDisponibles;
    private UUID idUsuario;

}
