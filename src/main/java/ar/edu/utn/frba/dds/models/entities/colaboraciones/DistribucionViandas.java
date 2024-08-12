package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.AccionSobreViandas;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.SolicitudApertura;
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

public class DistribucionViandas implements ContribucionHumana{
    private Heladera heladeraOrigen;
    private Heladera heladeraDestino;
    private Integer cantidadViandas;
    private String motivo;
    private LocalDate fechaDistribucion;
    private TarjetaHumano solicitante;


    public DistribucionViandas(Heladera heladeraOrigen, Heladera heladeraDestino, Integer cantidadViandas, String motivo, LocalDate fechaDistribucion) {
        this.heladeraOrigen = heladeraOrigen;
        this.heladeraDestino = heladeraDestino;
        this.cantidadViandas = cantidadViandas;
        this.motivo = motivo;
        this.fechaDistribucion = fechaDistribucion;
    }

    public DistribucionViandas(Integer cantidadViandas) {
        this.cantidadViandas = cantidadViandas;
    }


    @Override
    public void contribuir() {
        SolicitudApertura soliApertura = new SolicitudApertura(solicitante,cantidadViandas, AccionSobreViandas.RETIRAR, heladeraOrigen );
        heladeraOrigen.agregarSolicitudApertura(soliApertura);
        SolicitudApertura soliApertura2 = new SolicitudApertura(solicitante,cantidadViandas, AccionSobreViandas.INGRESAR, heladeraDestino);
        heladeraDestino.agregarSolicitudApertura(soliApertura2);
    }

    @Override
    public double calcularPuntaje() {
        ConstantesMultiplicativas constantes = new ConstantesMultiplicativas();
        return constantes.getCteViandasDistribuidas() * cantidadViandas;
    }




}
