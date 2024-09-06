package ar.edu.utn.frba.dds.models.entities.colaboraciones;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ofrecimiento_producto")
public class OfrecerProductoOServicio extends Contribucion {
    @OneToOne
    @JoinColumn(name = "id_oferta", referencedColumnName = "id_oferta")
    private Oferta oferta;

    public static OfrecerProductoOServicio of(Oferta oferta) {
        return new OfrecerProductoOServicio(oferta);
    }

    @Override
    public Double calcularPuntaje() {
        return 0.0;
    }
}
