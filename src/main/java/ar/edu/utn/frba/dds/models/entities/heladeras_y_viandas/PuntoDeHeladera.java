package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PuntoDeHeladera {
    @Column(name = "punto_de_heladera")
    private String nombreDePunto;
}
