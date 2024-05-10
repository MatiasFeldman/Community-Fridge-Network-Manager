package ar.edu.utn.frba.dds.personas;

import ar.edu.utn.frba.dds.colaboraciones.Tarjeta;
import ar.edu.utn.frba.dds.ubicacion.Direccion;
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
    private Tarjeta tarjeta;

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
}
