package ar.edu.utn.frba.dds.models.entities.colaboraciones;


import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ofrecimiento_producto")
public class OfrecerProductoOServicio implements Contribucion{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_contribucion")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_juridica", referencedColumnName = "id_juridica")
    private Juridica juridica;

    @OneToOne
    @JoinColumn(name = "id_oferta", referencedColumnName = "id_oferta")
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
