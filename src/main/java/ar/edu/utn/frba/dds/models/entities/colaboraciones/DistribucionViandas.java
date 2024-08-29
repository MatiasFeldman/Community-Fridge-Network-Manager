package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class DistribucionViandas implements ContribucionHumana {
    private Heladera heladeraOrigen;
    private Heladera heladeraDestino;
    private Integer cantidadViandas;
    private String motivo;
    private LocalDate fechaDistribucion;
    private TarjetaHumano solicitante;
    private Boolean distribuidas;


    public static DistribucionViandas of(Heladera origen, Heladera destino, Integer cant, String motivo, TarjetaHumano tarjetaSoli) {
        return DistribucionViandas
                .builder()
                .heladeraOrigen(origen)
                .heladeraDestino(destino)
                .cantidadViandas(cant)
                .motivo(motivo)
                .fechaDistribucion(null)
                .solicitante(tarjetaSoli)
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
    public double calcularPuntaje() {
        ConstantesMultiplicativas constantes = new ConstantesMultiplicativas();
        return distribuidas ? constantes.getCteViandasDistribuidas() * cantidadViandas : 0;
    }


}
