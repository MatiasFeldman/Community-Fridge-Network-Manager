package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Comida;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.SolicitudApertura;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Vianda;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@NoArgsConstructor
public class DonacionDeVianda implements ContribucionHumana{
    private Vianda vianda;
    private TarjetaHumano solicitante;


    public DonacionDeVianda(Comida comida, LocalDate fechaVencimiento,Float calorias,Float peso, Heladera heladera, TarjetaHumano solicitante) {
        this.vianda = new Vianda(comida, fechaVencimiento, LocalDate.now() ,heladera,calorias,peso,false);
        this.solicitante = solicitante;
    }


    @Override
    public void contribuir(){
        SolicitudApertura soliApertura = new SolicitudApertura(solicitante, 1);
        soliApertura.setVianda(vianda);
        vianda.getHeladeraDondeSeEncuentra().agregarSolicitudApertura(soliApertura);
    }

    @Override
    public double calcularPuntaje() {
        ConstantesMultiplicativas constantes = new ConstantesMultiplicativas();
        return constantes.getCteViandasDonadas();
    }



}
