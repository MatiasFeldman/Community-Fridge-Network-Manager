package ar.edu.utn.frba.dds.models.entities.personas;

import ar.edu.utn.frba.dds.converter.DireccionConverter;
import ar.edu.utn.frba.dds.exceptions.PuntosInsuficientesException;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.Contribucion;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.Oferta;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.models.entities.ubicacion.RecomendarPuntos;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name = "juridica")
@Getter
public class Juridica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_juridica")
    private Long idJuridica;

    @OneToOne(fetch = FetchType.EAGER)
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

    @Convert(converter = DireccionConverter.class)
    @Column(name = "direccion")
    private Direccion direccion;

    @Column(name = "puntos_canjeados")
    private Double puntosCanjeados;

    @Column(name = "puntos_ganados")
    private Double puntosGanados;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_contribucion")
    private List<Contribucion> contribuciones = new ArrayList<>();

    @Transient
    private RecomendarPuntos recomendador;

    public Juridica(RecomendarPuntos recomendador) {
        this.recomendador = recomendador;
    }

    public List<Coordenada> solicitarRecomendacionParaHeladera(Coordenada coord, double radio) throws IOException, InterruptedException {
        return recomendador.solicitarRecomendacionParaHeladera(coord, radio);
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
        puntosGanados += contribucion.calcularPuntaje();
    }

    public Long getId() {
        return user.getId();
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
