package ar.edu.utn.frba.dds.models.entities.personas;

import ar.edu.utn.frba.dds.dtos.humanos.HumanoInputDTO;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.*;
import ar.edu.utn.frba.dds.models.entities.suscripciones.ObserverSuscripcion;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.exceptions.PuntosInsuficientesException;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Getter
@AllArgsConstructor
@Builder
@Setter
@NoArgsConstructor
@Entity
@Table(name = "humano")
public class Humano extends ObserverSuscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_humano")
    private Long idHumano;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario user;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tarjeta")
    private TarjetaHumano tarjeta = null; // HAY BIDIRECCIONALIDAD DE DATOS

    @OneToMany(mappedBy = "humano", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_atributo")
    private List<AtributoHumanoRespondido> atributosObligatorios = new ArrayList<>();

    @OneToMany(mappedBy = "humano", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_atributo")
    private List<AtributoHumanoRespondido> atributosOpcionales = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "humano", fetch = FetchType.LAZY)
    @JoinColumn(name = "id_contacto")
    private List<Contacto> mediosDeContacto;

    @Column(name = "puntos_canjeados")
    private Double puntosCanjeados;

    @Column(name = "puntos_ganados")
    private Double puntosGanados;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "humano", fetch = FetchType.LAZY)
    @JoinColumn(name = "id_contribucion")
    private List<Contribucion> contribuciones = new ArrayList<>();

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
                .puntosCanjeados(0.0)
                .puntosGanados(0.0)
                .contribuciones(dto.getContribuciones())
                .user(dto.getUser())
                .user(dto.getUser())
                .build();
    }

    public void generarContacto(Contacto contacto) {
        this.mediosDeContacto.add(contacto);
    }

    public void generarAtributo(TipoAtributo tipo, String nombreAtributo, String valor) {
        if (tipo == TipoAtributo.OBLIGATORIO) {
            this.atributosObligatorios.add(AtributoHumanoRespondido.create(nombreAtributo, valor));
        } else {
            this.atributosOpcionales.add(AtributoHumanoRespondido.create(nombreAtributo, valor));
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

    public void agregarContribucion(Contribucion contribucion) {
        this.contribuciones.add(contribucion);
        puntosGanados+= contribucion.calcularPuntaje();
    }

    public String getDocumento(String tipo) {
        return this.atributosOpcionales
                .stream()
                .filter(atributo -> atributo.getNombreAtributo().equals(tipo))
                .findFirst()
                .get()
                .getValor();
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

    public Long getIdUsuario(){
        return this.user.getId();
    }

    public static Humano crearVacio(){
        return Humano
                .builder()
                .atributosObligatorios(new ArrayList<>())
                .atributosOpcionales(new ArrayList<>())
                .mediosDeContacto(new ArrayList<>())
                .puntosCanjeados(0.0)
                .puntosGanados(0.0)
                .contribuciones(new ArrayList<>())
                .build();
    }

}
