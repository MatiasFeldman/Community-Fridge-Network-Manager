package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.exceptions.UsuarioSinTarjetaException;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.repositories.tarjetas.TarjetasRepository;
import lombok.*;

import javax.persistence.*;
import java.util.Optional;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "donacion_de_vianda")
public class DonacionDeVianda implements Contribucion{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_contribucion")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_colaborador", referencedColumnName = "id_humano")
    private Humano colaborador;

    @Column(name = "finalizada")
    private Boolean finalizada;

    @ManyToOne
    @JoinColumn(name = "heladera_id", referencedColumnName = "id_heladera")
    private Heladera heladera;

    @Column(name = "activa")
    private Boolean activa;


    public static DonacionDeVianda of(Heladera heladera, Humano colaborador) {
        return DonacionDeVianda
                .builder()
                .heladera(heladera)
                .colaborador(colaborador)
                .finalizada(false)
                .activa(true)
                .build();
    }

    public static DonacionDeVianda of(Heladera heladera, Humano colaborador, Boolean finalizada) {
        return DonacionDeVianda
                .builder()
                .heladera(heladera)
                .colaborador(colaborador)
                .finalizada(finalizada)
                .activa(true)
                .build();
    }

    public static DonacionDeVianda ofFinalizada() {
        return DonacionDeVianda
                .builder()
                .finalizada(true)
                .activa(true)
                .build();
    }

    @Override
    public Double calcularPuntaje() {
        ConstantesMultiplicativas constantes = new ConstantesMultiplicativas();

        return finalizada ? constantes.getCteViandasDonadas() : 0;
    }


    public Long getColaboradorId() {
        return this.colaborador.getIdHumano();
    }
}
