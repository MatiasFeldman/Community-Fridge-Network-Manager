package ar.edu.utn.frba.dds.models.entities.personas;

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
public class Atributo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atributo")
    private Long id;

    @Column(name = "nombre_atributo", nullable = false)
    private String nombre;

    public Atributo(String nombreAtributo){
        this.nombre = nombreAtributo;
    }

}
