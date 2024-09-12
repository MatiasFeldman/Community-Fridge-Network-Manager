package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "heladera_a_cargo")
public class HacerseCargoHeladera implements Contribucion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_contribucion")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_juridica", referencedColumnName = "id_juridica")
    private Juridica juridica;

    @ManyToOne
    @JoinColumn(name = "id_heladera", referencedColumnName = "id_heladera")
    private Heladera heladera;

    public static HacerseCargoHeladera of(Heladera heladera, Juridica colaborador) {
        return HacerseCargoHeladera
                .builder()
                .heladera(heladera)
                .juridica(colaborador)
                .build();
    }

    @Override
    public Double calcularPuntaje() {
        if (heladera.getActiva()) {
            ConstantesMultiplicativas constantes = new ConstantesMultiplicativas();
            return constantes.getCteHeladeras() * heladera.mesesActiva();
        } else {
            return 0.0;
        }
    }


}
