package ar.edu.utn.frba.dds.models.entities.personas;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "atributo_humano")
public class AtributoHumano {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atributo_humano")
    private Long id;

    @Column(name = "nombre_atributo", nullable = false)
    private String nombreAtributo;

    @Setter
    @Column(name = "valor_atributo")
    private String valorAtributo;

    @ManyToOne
    @JoinColumn(name = "id_humano", nullable = false)
    private Humano humano;

    public AtributoHumano(String nombreAtributo) {
        this.nombreAtributo = nombreAtributo;
    }

    public AtributoHumano(String nombreAtributo, String valorAtributo) {
        this.nombreAtributo = nombreAtributo;
        this.valorAtributo = valorAtributo;
    }

}
