package ar.edu.utn.frba.dds.models.entities.colaboraciones;


import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "ofrecimiento_producto")
public class OfrecerProductoOServicio extends Persistente implements Contribucion{

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_juridica", referencedColumnName = "id")
    private Juridica juridica;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_oferta", referencedColumnName = "id")
    private Oferta oferta;

    public static OfrecerProductoOServicio of(Oferta oferta, Juridica colaborador) {
        return OfrecerProductoOServicio
                .builder()
                .oferta(oferta)
                .juridica(colaborador)
                .build();
    }

    @Override
    public Double calcularPuntaje() {
        return 0.0;
    }
}
