package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import lombok.*;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "donacion_de_vianda")
public class DonacionDeVianda extends Persistente implements Contribucion{

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_colaborador", referencedColumnName = "id")
    private ColaboradorHumano colaborador;

    @Column(name = "finalizada")
    private Boolean finalizada;

    @ManyToOne()
    @JoinColumn(name = "heladera_id", referencedColumnName = "id")
    private Heladera heladera;

    @Column(name = "activa")
    private Boolean activa;


    public static DonacionDeVianda of(Heladera heladera, ColaboradorHumano colaborador) {
        return DonacionDeVianda
                .builder()
                .heladera(heladera)
                .colaborador(colaborador)
                .finalizada(false)
                .activa(true)
                .build();
    }

    public static DonacionDeVianda of(Heladera heladera, ColaboradorHumano colaborador, Boolean finalizada) {
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
