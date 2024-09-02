package ar.edu.utn.frba.dds.models.entities.personas;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PersonaVulnerable {
    private Long id;
    private String nombre;
    private LocalDate fechaNacimiento;
    private LocalDate fechaDeRegistro;
    private Direccion domicilio;
    private String nroDocumento;
    private Integer menoresACargo;
    private Humano registradaPor;
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
