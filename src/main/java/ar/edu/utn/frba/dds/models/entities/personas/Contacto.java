package ar.edu.utn.frba.dds.models.entities.personas;

import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
@Table(name = "contacto")
public class Contacto extends Persistente {

    @Embedded
    private TipoContacto tipoContacto;

    @Column(name = "valor")
    private String valorContacto;

    public static Contacto of(String tipoCon, String valor){
        TipoContacto tipo = new TipoContacto(tipoCon);
        return new Contacto(tipo, valor);
    }
}
