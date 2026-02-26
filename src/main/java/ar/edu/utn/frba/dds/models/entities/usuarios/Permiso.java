package ar.edu.utn.frba.dds.models.entities.usuarios;

import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "permiso")
public class Permiso extends Persistente {
    @Column(name = "nombre", nullable = false)
    private String nombre;
}
