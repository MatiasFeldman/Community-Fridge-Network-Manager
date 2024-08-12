package ar.edu.utn.frba.dds.models.entities.personas;

import ar.edu.utn.frba.dds.dtos.humanos.HumanoInputDTO;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.ContribucionHumana;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.Oferta;
import ar.edu.utn.frba.dds.models.entities.suscripciones.ObserverSuscripcion;
import ar.edu.utn.frba.dds.models.entities.comandos.AvisarTecnico;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaHumano;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Accionador;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.DenunciaFallaTecnica;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.factories.AccionadorFactory;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import ar.edu.utn.frba.dds.models.repositories.ofertas.imp.OfertasRepository;
import ar.edu.utn.frba.dds.exceptions.PuntosInsuficientesException;
import lombok.*;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
@Setter
@Data
@EqualsAndHashCode(of = "tarjeta")//no lo temrino de entender
public class Humano extends ObserverSuscripcion {
    private ArrayList<AtributoHumano> atributosObligatorios;
    private ArrayList<Contacto> mediosDeContacto;
    private ArrayList<AtributoHumano> atributosOpcionales;
    private double puntosCanjeados;
    private double puntosGanados;
    private ArrayList<ContribucionHumana> contribuciones;
    private OfertasRepository ofertasDisponibles;
    private IncidentesRepository incidentesRepository;
    private UUID idUsuario;
    private TarjetaHumano tarjeta = null;
    private Usuario user;

    public void setTarjeta(TarjetaHumano tarjeta) {
        this.tarjeta = tarjeta;
        this.tarjeta.setDuenio(this);
    }

    public Humano(OfertasRepository ofertas) {
        this.ofertasDisponibles = ofertas;
        this.atributosObligatorios = new ArrayList<>();
        this.mediosDeContacto = new ArrayList<>();
        this.atributosOpcionales = new ArrayList<>();
        this.contribuciones = new ArrayList<>();
    }

    public static Humano create(HumanoInputDTO dto) {
        return Humano
                .builder()
                .atributosObligatorios(dto.getAtributosObligatorios())
                .atributosOpcionales(dto.getAtributosOpcionales())
                .mediosDeContacto(dto.getMediosDeContacto())
                .puntosCanjeados(0)
                .puntosGanados(0)
                .contribuciones(dto.getContribuciones())
                .ofertasDisponibles(dto.getOfertasDisponibles())
                .idUsuario(dto.getIdUsuario())
                .user(dto.getUser())
                .build();
    }

    public void generarContacto(Contacto contacto) {
        this.mediosDeContacto.add(contacto);
    }

    public void generarAtributo(TipoAtributo tipo, String nombreAtributo, String valor) {
        if (tipo == TipoAtributo.OBLIGATORIO) {
            this.atributosObligatorios.add(new AtributoHumano(nombreAtributo, valor));
        } else {
            this.atributosOpcionales.add(new AtributoHumano(nombreAtributo, valor));
        }
    }

    public double calcularPuntaje() {
        return puntosGanados - puntosCanjeados;
    }


    public void canjearOferta(Oferta oferta) {
        if (oferta.getPuntosNecesarios() > this.calcularPuntaje()) {
            throw new PuntosInsuficientesException("No tiene los puntos necesarios para canjear la oferta");
        }
        ofertasDisponibles.canjearOferta(oferta);
        this.puntosCanjeados += oferta.getPuntosNecesarios();

    }

    public void agregarContribucion(ContribucionHumana contribucion) {
        this.contribuciones.add(contribucion);
        puntosGanados+= contribucion.calcularPuntaje();
    }

}
