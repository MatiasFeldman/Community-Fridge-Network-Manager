package ar.edu.utn.frba.dds.models.entities.usuarios;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "permiso")
public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permiso")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;
}
