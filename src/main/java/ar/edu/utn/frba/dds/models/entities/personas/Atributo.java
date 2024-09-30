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
@Setter
public class Atributo extends Persistente {

    @Column(name = "nombre_atributo", nullable = false)
    private String nombre;

    private TipoCampoAtributo tipoCampo;

    private TipoAtributo tipo;

    public static Atributo create(String nombre, TipoAtributo tipo, TipoCampoAtributo campo){
        return Atributo
                .builder()
                .nombre(nombre)
                .tipo(tipo)
                .tipoCampo(campo)
                .presente(true)
                .build();
    }

}
