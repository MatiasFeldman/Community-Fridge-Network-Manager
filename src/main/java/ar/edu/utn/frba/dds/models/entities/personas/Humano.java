package ar.edu.utn.frba.dds.models.entities.personas;

import ar.edu.utn.frba.dds.dtos.humanos.HumanoInputDTO;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.ContribucionHumana;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.Oferta;
import ar.edu.utn.frba.dds.models.entities.suscripciones.ObserverSuscripcion;
import ar.edu.utn.frba.dds.models.repositories.ofertas.imp.OfertasRepository;
import ar.edu.utn.frba.dds.exceptions.PuntosInsuficientesException;
import lombok.*;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class Humano extends ObserverSuscripcion {
    private ArrayList<AtributoHumano> atributosObligatorios;
    private ArrayList<Contacto> mediosDeContacto;
    private ArrayList<AtributoHumano> atributosOpcionales;
    private double puntosCanjeados;
    private ArrayList<ContribucionHumana> contribuciones;
    private OfertasRepository ofertasDisponibles;
    private UUID idUsuario;

    public Humano(OfertasRepository ofertas) {
        this.ofertasDisponibles = ofertas;
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

    public String nombre() {
        return this.atributosObligatorios.stream().filter(atributo -> atributo.getNombreAtributo().equals("Nombre")).findFirst().get().getValorAtributo();
    }

    public String apellido() {
        return this.atributosObligatorios.stream().filter(atributo -> atributo.getNombreAtributo().equals("Apellido")).findFirst().get().getValorAtributo();
    }
}
