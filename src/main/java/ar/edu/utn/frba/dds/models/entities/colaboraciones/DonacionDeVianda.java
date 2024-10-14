package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "donacion_de_vianda")
public class DonacionDeVianda extends Persistente implements Contribucion {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_colaborador", referencedColumnName = "id")
    private ColaboradorHumano colaborador;

    @Column(name = "finalizada")
    private Boolean finalizada;

    @ManyToOne()
    @JoinColumn(name = "heladera_id", referencedColumnName = "id")
    private Heladera heladera;

    @Column(name = "fecha")
    private LocalDate fecha;


    public static DonacionDeVianda of(Heladera heladera, ColaboradorHumano colaborador) {
        DonacionDeViandaBuilder builder = DonacionDeVianda
                .builder()
                .heladera(heladera)
                .colaborador(colaborador)
                .finalizada(false)
                .fecha(LocalDate.now())
                .presente(true);
        heladera.agregarViandas(1);
        return builder.build();
    }

    public static DonacionDeVianda of(Heladera heladera, ColaboradorHumano colaborador, Boolean finalizada) {
        DonacionDeViandaBuilder builder = DonacionDeVianda
                .builder()
                .heladera(heladera)
                .colaborador(colaborador)
                .finalizada(finalizada)
                .fecha(LocalDate.now())
                .presente(true);
        heladera.agregarViandas(1);
        return builder.build();
    }

    public static DonacionDeVianda ofFinalizada(ColaboradorHumano colaboradorHumano) {
        return DonacionDeVianda
                .builder()
                .finalizada(true)
                .colaborador(colaboradorHumano)
                .fecha(LocalDate.now())
                .presente(true)
                .build();
    }

    @Override
    public Double calcularPuntaje() {
        ConstantesMultiplicativas constantes = new ConstantesMultiplicativas();

        return finalizada ? constantes.getCteViandasDonadas() : 0;
    }


    public Long getColaboradorId() {
        return this.colaborador.getId();
    }
}
