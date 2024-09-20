package ar.edu.utn.frba.dds.models.entities.personas;

import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "atributo")
@AllArgsConstructor
@Getter
public class Atributo extends Persistente {

    @Column(name = "nombre_atributo", nullable = false)
    private String nombre;

}
