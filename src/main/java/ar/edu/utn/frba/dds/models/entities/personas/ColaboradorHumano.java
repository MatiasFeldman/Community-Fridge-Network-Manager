package ar.edu.utn.frba.dds.models.entities.personas;

import ar.edu.utn.frba.dds.dtos.humanos.HumanoInputDTO;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.*;
import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.exceptions.PuntosInsuficientesException;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Getter
@AllArgsConstructor
@SuperBuilder
@Setter
@NoArgsConstructor
@Entity
@Table(name = "humano")
public class ColaboradorHumano extends Persistente {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "duenio")
    private List<TarjetaColaborador> tarjetas = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_atributo_obligatorio")
    private List<AtributoHumanoRespondido> atributosObligatorios = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_atributo_opcional")
    private List<AtributoHumanoRespondido> atributosOpcionales = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_contacto")
    private List<Contacto> mediosDeContacto;

    @Embedded
    private Direccion direccion;

    @Column(name = "puntos_canjeados")
    private Double puntosCanjeados;

    @Column(name = "puntos_ganados")
    private Double puntosGanados;

    public void agregarTarejta(TarjetaColaborador tarjeta) {
        tarjeta.setDuenio(this);
        this.tarjetas.add(tarjeta);
    }

    public static ColaboradorHumano create(HumanoInputDTO dto) {
        return ColaboradorHumano
                .builder()
                .atributosObligatorios(dto.getAtributosObligatorios())
                .atributosOpcionales(dto.getAtributosOpcionales())
                .mediosDeContacto(dto.getMediosDeContacto())
                .direccion(dto.getDireccion())
                .puntosCanjeados(0.0)
                .puntosGanados(0.0)
                .user(dto.getUser())
                .build();
    }

    public void generarContacto(Contacto contacto) {
        this.mediosDeContacto.add(contacto);
    }

    public void generarAtributo(TipoAtributo tipo, String nombreAtributo, String valor, TipoCampoAtributo tipoCampo) {
        if (tipo == TipoAtributo.OBLIGATORIO) {
            this.atributosObligatorios.add(AtributoHumanoRespondido.create(nombreAtributo, valor, tipo, tipoCampo));
        } else {
            this.atributosOpcionales.add(AtributoHumanoRespondido.create(nombreAtributo, valor, tipo, tipoCampo));
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

    public void sumarPuntaje(Contribucion contribucion) {
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

    public static ColaboradorHumano crearVacio(){
        return ColaboradorHumano
                .builder()
                .atributosObligatorios(new ArrayList<>())
                .atributosOpcionales(new ArrayList<>())
                .mediosDeContacto(new ArrayList<>())
                .puntosCanjeados(0.0)
                .puntosGanados(0.0)
                .build();
    }

}
