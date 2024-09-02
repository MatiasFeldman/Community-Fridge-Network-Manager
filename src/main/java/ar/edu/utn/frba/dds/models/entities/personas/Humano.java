package ar.edu.utn.frba.dds.models.entities.personas;

import ar.edu.utn.frba.dds.dtos.humanos.HumanoInputDTO;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.*;
import ar.edu.utn.frba.dds.models.entities.suscripciones.ObserverSuscripcion;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import ar.edu.utn.frba.dds.models.repositories.ofertas.imp.OfertasRepository;
import ar.edu.utn.frba.dds.exceptions.PuntosInsuficientesException;
import lombok.*;

import java.util.ArrayList;


@Getter
@AllArgsConstructor
@Builder
@Setter
@NoArgsConstructor
public class Humano extends ObserverSuscripcion {
    private ArrayList<AtributoHumano> atributosObligatorios;
    private ArrayList<Contacto> mediosDeContacto;
    private ArrayList<AtributoHumano> atributosOpcionales;
    private double puntosCanjeados;
    private double puntosGanados;
    private ArrayList<ContribucionHumana> contribuciones;
    private Long idUsuario;
    private TarjetaHumano tarjeta = null;
    private Usuario user;

    public void setTarjeta(TarjetaHumano tarjeta) {
        this.tarjeta = tarjeta;
        this.tarjeta.setDuenio(this);
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
                .idUsuario(dto.getUser().getId())
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
        this.puntosCanjeados += oferta.getPuntosNecesarios();

    }

    public void agregarContribucion(ContribucionHumana contribucion) {
        this.contribuciones.add(contribucion);
        puntosGanados+= contribucion.calcularPuntaje();
    }

    public String getDocumento(String tipo) {
        return this.atributosOpcionales
                .stream()
                .filter(atributo -> atributo.getNombreAtributo().equals(tipo))
                .findFirst()
                .get()
                .getValorAtributo();
    }

    public String getUsername(){
        return this.user.getUser();
    }

    public String getMedioDeContacto(String medio){
        return this.mediosDeContacto
                .stream()
                .filter(contacto -> contacto.getTipoContacto().equals(medio))
                .findFirst()
                .get()
                .getValorContacto();
    }


}
