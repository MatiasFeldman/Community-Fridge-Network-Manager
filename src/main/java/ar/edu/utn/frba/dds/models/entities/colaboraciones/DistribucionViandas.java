package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "distribucion_viandas")
public class DistribucionViandas implements Contribucion{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_contribucion")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_colaborador", referencedColumnName = "id_humano")
    private Humano colaborador;

    @ManyToOne
    @JoinColumn(name = "id_heladera_origen", referencedColumnName = "id_heladera")
    private Heladera heladeraOrigen;

    @ManyToOne
    @JoinColumn(name = "id_heladera_destino", referencedColumnName = "id_heladera")
    private Heladera heladeraDestino;

    @Column(name = "cantidad_viandas")
    private Integer cantidadViandas;

    @Column(name = "motivo")
    private String motivo;

    @Column(name = "fecha_distribucion")
    private LocalDate fechaDistribucion;

    @Column(name = "distribuidas")
    private Boolean distribuidas;

    @Column(name = "activa")
    private Boolean activa;


    public static DistribucionViandas of(Heladera origen, Heladera destino, Integer cant, String motivo, Humano humano) {
        return DistribucionViandas
                .builder()
                .colaborador(humano)
                .heladeraOrigen(origen)
                .heladeraDestino(destino)
                .cantidadViandas(cant)
                .motivo(motivo)
                .fechaDistribucion(null)
                .distribuidas(false)
                .build();
    }

    public static DistribucionViandas ofCargaMasiva(Integer cantViandas, Humano humano) {
        return DistribucionViandas
                .builder()
                .colaborador(humano)
                .cantidadViandas(cantViandas)
                .distribuidas(true)
                .build();
    }

    @Override
    public Double calcularPuntaje() {
        ConstantesMultiplicativas constantes = new ConstantesMultiplicativas();
        return distribuidas ? constantes.getCteViandasDistribuidas() * cantidadViandas : 0;
    }


    public Long getColaboradorId() {
        return this.colaborador.getIdHumano();
    }

}
