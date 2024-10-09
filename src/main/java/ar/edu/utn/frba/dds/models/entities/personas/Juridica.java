package ar.edu.utn.frba.dds.models.entities.personas;

import ar.edu.utn.frba.dds.dtos.humanos.HumanoInputDTO;
import ar.edu.utn.frba.dds.dtos.juridico.JuridicoInputDTO;
import ar.edu.utn.frba.dds.exceptions.PuntosInsuficientesException;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.Contribucion;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.Oferta;
import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.models.entities.ubicacion.RecomendarPuntos;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.IOException;
import java.util.List;
import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name = "juridica")
@SuperBuilder
@Getter
@Setter
public class Juridica extends Persistente {

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario user;

    @Column(name = "razon_social")
    private String razonSocial;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @Column(name = "rubro")
    private String rubro;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Contacto> mediosDeContacto;

    @Embedded
    private Direccion direccion;

    @Column(name = "puntos_canjeados")
    private Double puntosCanjeados;

    @Column(name = "puntos_ganados")
    private Double puntosGanados;


    public List<Coordenada> solicitarRecomendacionParaHeladera(Coordenada coord, double radio) throws IOException, InterruptedException {
        return ServiceLocator.instanceOf(RecomendarPuntos.class).solicitarRecomendacionParaHeladera(coord, radio);
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
        puntosGanados += contribucion.calcularPuntaje();
    }

    public Long getId() {
        return user.getId();
    }

    public String getMedioDeContacto(String medio){
        return this.mediosDeContacto
                .stream()
                .filter(contacto -> contacto.getTipoContacto().getNombre().equals(medio))
                .findFirst()
                .get()
                .getValorContacto();
    }

    public static Juridica create(JuridicoInputDTO dto) {
        return Juridica
                .builder()
                .tipo(dto.getTipo())
                .razonSocial(dto.getRazonSocial())
                .rubro(dto.getRubro())
                .mediosDeContacto(dto.getMediosDeContacto())
                .direccion(dto.getDireccion())
                .puntosCanjeados(0.0)
                .puntosGanados(0.0)
                .user(dto.getUser())
                .build();
    }
}

