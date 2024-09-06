package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "distribucion_viandas")
public class DistribucionViandas extends Contribucion {
    @Transient
    private Heladera heladeraOrigen;
    @Transient
    private Heladera heladeraDestino;

    @Column(name = "cantidad_viandas")
    private Integer cantidadViandas;

    @Column(name = "motivo")
    private String motivo;

    @Column(name = "fecha_distribucion")
    private LocalDate fechaDistribucion;

    @Column(name = "distribuidas")
    private Boolean distribuidas;


    public static DistribucionViandas of(Heladera origen, Heladera destino, Integer cant, String motivo) {
        return DistribucionViandas
                .builder()
                .heladeraOrigen(origen)
                .heladeraDestino(destino)
                .cantidadViandas(cant)
                .motivo(motivo)
                .fechaDistribucion(null)
                .distribuidas(false)
                .build();
    }

    public static DistribucionViandas ofCargaMasiva(Integer cantViandas){
        return DistribucionViandas
                .builder()
                .cantidadViandas(cantViandas)
                .distribuidas(true)
                .build();
    }


    public DistribucionViandas(Integer cantidadViandas) {
        this.cantidadViandas = cantidadViandas;
    }


    @Override
    public Double calcularPuntaje() {
        ConstantesMultiplicativas constantes = new ConstantesMultiplicativas();
        return distribuidas ? constantes.getCteViandasDistribuidas() * cantidadViandas : 0;
    }


}
