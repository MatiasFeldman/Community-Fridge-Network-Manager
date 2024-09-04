package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.exceptions.UsuarioSinTarjetaException;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.repositories.tarjetas.TarjetasRepository;
import lombok.*;

import java.util.Optional;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class DonacionDeVianda extends Contribucion {
    private TarjetaHumano solicitante;
    @Setter
    private Boolean finalizada;
    @Getter
    private Heladera heladera;


    public static DonacionDeVianda of(Heladera heladera, TarjetaHumano solicitante) {
        return DonacionDeVianda
                .builder()
                .heladera(heladera)
                .solicitante(solicitante)
                .finalizada(false)
                .build();
    }

    public static DonacionDeVianda ofFinalizada() {
        return DonacionDeVianda
                .builder()
                .finalizada(true)
                .build();
    }

    @Override
    public Double calcularPuntaje() {
        ConstantesMultiplicativas constantes = new ConstantesMultiplicativas();

        return finalizada ? constantes.getCteViandasDonadas() : 0;
    }


}
