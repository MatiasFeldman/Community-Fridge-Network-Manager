package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
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
public class HacerseCargoHeladera extends Persistente implements Contribucion {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_juridica", referencedColumnName = "id")
    private Juridica juridica;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_heladera", referencedColumnName = "id")
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
