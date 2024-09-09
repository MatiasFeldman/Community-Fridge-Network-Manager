package ar.edu.utn.frba.dds.models.entities.suscripciones.tipo_suscripciones;

import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.Mensaje;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "suscripcion")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipo")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class Suscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_suscripcion")
    private Long id;

    @Column(name = "cuerpo")
    private String cuerpo;

    @Column(name = "destinatario")
    private String destinatario;

    @Column(name = "cantidad")
    protected Integer cantidad;

    public Mensaje getMensaje(){
        return new Mensaje(cuerpo, destinatario);
    }

    public abstract Boolean verificarCondicion(Integer capActual, Integer cantActual);
}
