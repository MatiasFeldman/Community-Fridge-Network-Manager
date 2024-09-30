package ar.edu.utn.frba.dds.models.entities.personas;

import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import ar.edu.utn.frba.dds.models.repositories.atributos_humano.dao.AtributosHumanoDAO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "atributo")
@AllArgsConstructor
@Getter
@SuperBuilder
public class Atributo extends Persistente {

    @Column(name = "nombre_atributo", nullable = false)
    private String nombre;

    private TipoAtributo tipo;

    public static Atributo create(String nombre, TipoAtributo tipo){
        return Atributo
                .builder()
                .nombre(nombre)
                .tipo(tipo)
                .presente(true)
                .build();
    }

}
