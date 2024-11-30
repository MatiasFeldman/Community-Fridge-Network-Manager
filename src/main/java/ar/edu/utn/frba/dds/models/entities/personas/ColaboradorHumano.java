package ar.edu.utn.frba.dds.models.entities.personas;

import ar.edu.utn.frba.dds.dtos.humanos.HumanoInputDTO;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.*;
import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import ar.edu.utn.frba.dds.models.entities.tecnicos.Tipo_documento;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.exceptions.PuntosInsuficientesException;
import ar.edu.utn.frba.dds.models.repositories.usuarios.UsuariosRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @JoinColumn(name = "id_usuario", nullable = false, referencedColumnName = "id")
    private Usuario user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "duenio")
    private List<TarjetaColaborador> tarjetas;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_atributo_obligatorio")
    private List<AtributoHumanoRespondido> atributosObligatorios = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_atributo_opcional")
    private List<AtributoHumanoRespondido> atributosOpcionales = new ArrayList<>();

    @ElementCollection
    private List<String> nombresMediosDeContacto = new ArrayList<>();

    @Embedded
    private Direccion direccion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento")
    private Tipo_documento tipoDocumento;


    @Column(name = "documento")
    private String documento;

    @Column(name = "puntos_canjeados")
    private Double puntosCanjeados;

    @Column(name = "puntos_ganados")
    private Double puntosGanados;

    public void agregarTarejta(TarjetaColaborador tarjeta) {
        tarjeta.setDuenio(this);
        tarjeta.setPrincipal(true);
        this.tarjetas.add(tarjeta);
    }

    public TarjetaColaborador getTarjetaPrincipal() {
        return this.tarjetas.stream().filter(TarjetaColaborador::getPrincipal).findFirst().get();
    }

    public static ColaboradorHumano create(HumanoInputDTO dto) {
        ServiceLocator.instanceOf(UsuariosRepository.class).guardar(dto.getUser());
        return ColaboradorHumano
                .builder()
                .atributosObligatorios(dto.getAtributosObligatorios())
                .atributosOpcionales(dto.getAtributosOpcionales())
                .nombresMediosDeContacto(dto.getNombresMediosDeContacto())
                .direccion(dto.getDireccion())
                .puntosCanjeados(0.0)
                .puntosGanados(0.0)
                .user(dto.getUser())
                .tarjetas(new ArrayList<>())
                .build();
    }

    public List<AtributoHumanoRespondido> getAllAtributos() {
        return Stream.concat(atributosObligatorios.stream(), atributosOpcionales.stream())
                .collect(Collectors.toList());
    }

    public void generarContacto(String medioDeContacto) {
        boolean yaExiste = nombresMediosDeContacto.stream()
                .anyMatch(medio -> medio.equalsIgnoreCase(medioDeContacto));
        if (!yaExiste) {
            nombresMediosDeContacto.add(medioDeContacto);
        }
    }
    public void actualizarMedioDeContacto(String tipoMedioContacto, String nuevoValor) {
        Optional<AtributoHumanoRespondido> medioContacto = getMedioDeContacto(tipoMedioContacto);
        if (medioContacto.isPresent()) {
            medioContacto.get().setValor(nuevoValor);
        } else {
            System.out.println("El medio de contacto '" + tipoMedioContacto + "' no fue encontrado.");
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
        puntosGanados += contribucion.calcularPuntaje();
    }

    public String getDocumento() {
        System.out.println("Buscando documento...");
        for (AtributoHumanoRespondido atributo : atributosOpcionales) {
            System.out.println("Atributo: " + atributo.getNombreAtributo() + " - " + atributo.getValor());
        }

        return atributosOpcionales.stream()
                .filter(atributo -> atributo.getNombreAtributo().equalsIgnoreCase("Documento"))
                .findFirst()
                .map(AtributoHumanoRespondido::getValor)
                .orElse(null);
    }

    public String getTipoDocumento() {
        return atributosOpcionales.stream()
                .filter(atributo -> atributo.getNombreAtributo().equalsIgnoreCase("Tipo Documento"))
                .findFirst()
                .map(AtributoHumanoRespondido::getValor)
                .orElse(null);
    }

    public String getUsername() {
        return this.user.getUser();
    }

    public List<AtributoHumanoRespondido> getMediosDeContacto() {
        return Stream.concat(atributosObligatorios.stream(), atributosOpcionales.stream())
                .filter(atributo -> nombresMediosDeContacto.contains(atributo.getNombreAtributo())) // Filtrar solo los que son medios de contacto
                .collect(Collectors.toList());
    }

    public Optional<AtributoHumanoRespondido> getMedioDeContacto(String nombreContacto) {
        return Stream.concat(atributosObligatorios.stream(), atributosOpcionales.stream())
                .filter(atributo -> atributo.getNombreAtributo().equalsIgnoreCase(nombreContacto)) // Buscar el contacto sin importar mayúsculas/minúsculas
                .findFirst(); // Devolver el primero que coincida (o vacío si no encuentra ninguno)
    }

    public Boolean tieneMedioDeContacto(String medio) {
        return this.nombresMediosDeContacto
                .stream()
                .anyMatch(contacto -> contacto.equalsIgnoreCase(medio));
    }

    public List<AtributoHumanoRespondido> getAtributosCompletos() {
        return Stream.concat(this.atributosObligatorios.stream(), this.atributosOpcionales.stream())
                .filter(AtributoHumanoRespondido::completo)
                .collect(Collectors.toList());
    }

    public List<AtributoHumanoRespondido> getAtributosIncompletos() {
        return Stream.concat(this.atributosObligatorios.stream(), this.atributosOpcionales.stream())
                .filter(atributo -> !atributo.completo())
                .collect(Collectors.toList());
    }

    public Long getIdUsuario() {
        return this.user.getId();
    }

    public static ColaboradorHumano crearVacio() {
        return ColaboradorHumano
                .builder()
                .atributosObligatorios(new ArrayList<>())
                .atributosOpcionales(new ArrayList<>())
                .nombresMediosDeContacto(new ArrayList<>())
                .puntosCanjeados(0.0)
                .puntosGanados(0.0)
                .build();
    }

    public TarjetaColaborador buscarTarjetaPorId(Long idTarjeta) {
        return this.tarjetas.stream().filter(tarjeta -> tarjeta.getId().equals(idTarjeta)).findFirst().get();
    }
}
