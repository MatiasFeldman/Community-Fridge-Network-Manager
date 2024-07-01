package ar.edu.utn.frba.dds.models.entities.personas;

import ar.edu.utn.frba.dds.dtos.humanos.HumanoInputDTO;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.ContribucionHumana;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.Oferta;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaHumano;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Accionador;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.DenunciaFallaTecnica;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.factories.AccionadorFactory;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import ar.edu.utn.frba.dds.models.repositories.ofertas.imp.OfertasRepository;
import ar.edu.utn.frba.dds.exceptions.PuntosInsuficientesException;
import lombok.*;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
@Setter
@Data
@EqualsAndHashCode(of = "tarjeta")//no lo temrino de entender
public class Humano {
    private ArrayList<AtributoHumano> atributosObligatorios;
    private ArrayList<Contacto> mediosDeContacto;
    private ArrayList<AtributoHumano> atributosOpcionales;
    private double puntosCanjeados;
    private ArrayList<ContribucionHumana> contribuciones;
    private OfertasRepository ofertasDisponibles;
    private IncidentesRepository incidentesRepository;
    private UUID idUsuario;
    private TarjetaHumano tarjeta = null;

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
                .puntosCanjeados(dto.getPuntosCanjeados())
                .contribuciones(dto.getContribuciones())
                .ofertasDisponibles(dto.getOfertasDisponibles())
                .idUsuario(dto.getIdUsuario())
                .build();
    }


    public void colaborar(ContribucionHumana contribucion) {
        contribucion.contribuir();
        contribuciones.add(contribucion);
    } // cada vez q la agregamos, le sumamos los puntos

    public void generarMedioDeContacto(TipoAtributo tipo, String nombreAtributo) {
        if (tipo == TipoAtributo.OBLIGATORIO) {
            this.atributosObligatorios.add(new AtributoHumano(nombreAtributo));
        } else {
            this.atributosOpcionales.add(new AtributoHumano(nombreAtributo));
        }
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
        return this.puntosGanados() - puntosCanjeados;
    }

    public double puntosGanados() {
        return contribuciones.stream().mapToDouble(ContribucionHumana::calcularPuntaje).sum();
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
    }

    public void reportarFallaTecnica(Heladera heladera){
        AccionadorFactory factory = new AccionadorFactory(incidentesRepository);
        Accionador accionador = factory.crearParaFallaTecnica(heladera);
        accionador.sucedeFallaTecnica(this, new DenunciaFallaTecnica());
    }

    public void reportarFallaTecnica(Heladera heladera, String descripcion, Image foto){
        AccionadorFactory factory = new AccionadorFactory(incidentesRepository);
        Accionador accionador = factory.crearParaFallaTecnica(heladera);
        accionador.sucedeFallaTecnica(this, new DenunciaFallaTecnica(descripcion, foto, LocalDateTime.now()));
    }


}
