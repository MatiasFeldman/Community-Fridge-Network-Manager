package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.SolicitudApertura;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
public class DistribucionViandas implements ContribucionHumana{
    private Heladera heladeraOrigen;
    private Heladera heladeraDestino;
    private Integer cantidadViandas;
    private String motivo;
    private LocalDate fechaDistribucion;
    private TarjetaHumano solicitante;


    public DistribucionViandas(Integer cantViandas){
        this.cantidadViandas = cantViandas;
    }


    @Override
    public void contribuir() {
        SolicitudApertura soliApertura = new SolicitudApertura(solicitante,-cantidadViandas );
        heladeraOrigen.agregarSolicitudApertura(soliApertura);
        SolicitudApertura soliApertura2 = new SolicitudApertura(solicitante,cantidadViandas );
        heladeraDestino.agregarSolicitudApertura(soliApertura2);
    }

    @Override
    public double calcularPuntaje() {
        ConstantesMultiplicativas constantes = new ConstantesMultiplicativas();
        return constantes.getCteViandasDistribuidas() * cantidadViandas;
    }




}
