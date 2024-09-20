package ar.edu.utn.frba.dds.models.entities.personas;

import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "contacto")
public class Contacto extends Persistente {

    @Column(name = "tipo")
    private String tipoContacto;

    @Column(name = "valor")
    private String valorContacto;

    public Contacto(String tipoContacto, String valorContacto) {
        this.tipoContacto = tipoContacto;
        this.valorContacto = valorContacto;
    }
}
