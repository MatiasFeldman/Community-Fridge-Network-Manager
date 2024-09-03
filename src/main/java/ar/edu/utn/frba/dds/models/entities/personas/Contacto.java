package ar.edu.utn.frba.dds.models.entities.personas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "contacto")
public class Contacto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contacto")
    private Long id_contacto;

    @Setter
    @Column(name = "tipo")
    private String tipoContacto;

    @Setter
    @Column(name = "valor")
    private String valorContacto;

    public Contacto(String tipoContacto, String valorContacto) {
        this.tipoContacto = tipoContacto;
        this.valorContacto = valorContacto;
    }
}
