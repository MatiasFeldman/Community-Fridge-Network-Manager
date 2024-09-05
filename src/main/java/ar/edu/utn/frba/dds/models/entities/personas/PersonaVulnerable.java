package ar.edu.utn.frba.dds.models.entities.personas;

import ar.edu.utn.frba.dds.converter.DireccionConverter;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import lombok.*;

import java.time.LocalDate;
import javax.persistence.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "persona_vulnerable")
public class PersonaVulnerable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona_vulnerable")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_humano", nullable = false)
    private Humano registradaPor;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "fecha_registro")
    private LocalDate fechaDeRegistro;

    @Convert(converter = DireccionConverter.class)
    @Column(name = "direccion")
    private Direccion domicilio;

    @Column(name = "numero_documento", nullable = false)
    private String nroDocumento;

    @Column(name = "menores_a_cargo")
    private Integer menoresACargo;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tarjeta")
    private TarjetaPersonaVulnerable tarjeta;

    public PersonaVulnerable(String nombre, LocalDate fechaNacimiento, LocalDate fechaDeRegistro, Direccion domicilio, String nroDocumento, Integer menoresACargo, Humano registradaPor) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaDeRegistro = fechaDeRegistro;
        this.domicilio = domicilio;
        this.nroDocumento = nroDocumento;
        this.menoresACargo = menoresACargo;
        this.registradaPor = registradaPor;
        this.tarjeta = null;
    }

    public static PersonaVulnerable of(String nombre, LocalDate fechaNacimiento, Direccion domicilio, String nroDocumento, Integer menoresACargo){
        return PersonaVulnerable
                .builder()
                .nombre(nombre)
                .fechaNacimiento(fechaNacimiento)
                .fechaDeRegistro(LocalDate.now())
                .domicilio(domicilio)
                .nroDocumento(nroDocumento)
                .registradaPor(null)
                .menoresACargo(menoresACargo)
                .tarjeta(null)
                .build();
    }

    public Long getIdPersonaVulnerable() {
        return tarjeta.getId();
    }
}
