package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Contribucion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contribucion")
    private Long idContribucion;

    public abstract Double calcularPuntaje();
}
