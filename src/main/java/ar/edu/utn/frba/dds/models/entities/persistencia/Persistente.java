package ar.edu.utn.frba.dds.models.entities.persistencia;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public class Persistente {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "presente")
    private Boolean presente;
}
