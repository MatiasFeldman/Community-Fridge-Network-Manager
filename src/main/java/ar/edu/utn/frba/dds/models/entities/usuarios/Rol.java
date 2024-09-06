package ar.edu.utn.frba.dds.models.entities.usuarios;

import java.util.ArrayList;

import javax.persistence.*;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name = "rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "permiso_de_rol",
            joinColumns = @JoinColumn(name = "id_rol"),
            inverseJoinColumns = @JoinColumn(name = "id_permiso")
    )
    private ArrayList<Permiso> permisos;

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
