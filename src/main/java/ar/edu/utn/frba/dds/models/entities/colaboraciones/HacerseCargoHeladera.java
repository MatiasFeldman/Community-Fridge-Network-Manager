package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class HacerseCargoHeladera implements ContribucionJuridica {
    private Heladera heladera;


    public static HacerseCargoHeladera of(Heladera heladera) {
        return HacerseCargoHeladera
                .builder()
                .heladera(heladera)
                .build();
    }

    @Override
    public double calcularPuntaje() {
        if (heladera.getActiva()) {
            ConstantesMultiplicativas constantes = new ConstantesMultiplicativas();
            return constantes.getCteHeladeras() * heladera.mesesActiva();
        } else {
            return 0;
        }
    }


}
