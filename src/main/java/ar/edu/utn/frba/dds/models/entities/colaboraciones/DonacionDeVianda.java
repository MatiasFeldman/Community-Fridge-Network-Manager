package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.exceptions.UsuarioSinTarjetaException;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.repositories.tarjetas.TarjetasRepository;
import lombok.*;

import javax.persistence.*;
import java.util.Optional;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "donacion_de_vianda")
public class DonacionDeVianda extends Contribucion {
    @Setter
    @Column(name = "finalizada")
    private Boolean finalizada;

    @Getter
    @ManyToOne
    @JoinColumn(name = "heladera_id", referencedColumnName = "id_heladera")
    private Heladera heladera;


    public static DonacionDeVianda of(Heladera heladera) {
        return DonacionDeVianda
                .builder()
                .heladera(heladera)
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
