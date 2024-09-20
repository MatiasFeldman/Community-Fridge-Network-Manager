package ar.edu.utn.frba.dds.models.entities.usuarios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name = "rol")
public class Rol extends Persistente {

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "permiso_de_rol",
            joinColumns = @JoinColumn(name = "id_rol", referencedColumnName = "id_rol"),
            inverseJoinColumns = @JoinColumn(name = "id_permiso", referencedColumnName = "id_permiso")
    )
    private List<Permiso> permisos;

    public Rol(String nombre){
        this.nombre = nombre;
        this.permisos = new ArrayList<Permiso>();
    }

    public static Rol valueOf(String nombre){
        return new Rol(nombre);
    }

    public boolean tienePermiso(String permiso){
        return permisos
                .stream()
                .anyMatch(p -> p.getNombre().equals(permiso));
    }
}
