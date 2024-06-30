package ar.edu.utn.frba.dds.models.entities.personas;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class PersonaVulnerable {
    private String nombre;
    private LocalDate fechaNacimiento;
    private LocalDate fechaDeRegistro;
    private Direccion domicilio;
    private String nroDocumento;
    private Integer menoresACargo;
    private Humano registradaPor;
    private TarjetaPersonaVulnerable tarjetaPersonaVulnerable;

    public PersonaVulnerable(String nombre, LocalDate fechaNacimiento, LocalDate fechaDeRegistro, Direccion domicilio, String nroDocumento, Integer menoresACargo, Humano registradaPor) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaDeRegistro = fechaDeRegistro;
        this.domicilio = domicilio;
        this.nroDocumento = nroDocumento;
        this.menoresACargo = menoresACargo;
        this.registradaPor = registradaPor;
        this.tarjetaPersonaVulnerable = null;
    }
}
