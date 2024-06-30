package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.SolicitudApertura;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Vianda;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class DonacionDeVianda implements ContribucionHumana{
    private Vianda vianda;
    private Heladera heladera;

    @Override
    public void contribuir(TarjetaHumano solicitante, Integer horasParaEjecutar, Integer cantidadDeVianda){
        SolicitudApertura soliApertura = new SolicitudApertura(solicitante, horasParaEjecutar, cantidadDeVianda);
        heladera.agregarSolicitudApertura(soliApertura);
    }

    @Override
    public double calcularPuntaje() {
        ConstantesMultiplicativas constantes = new ConstantesMultiplicativas();
        return constantes.getCteViandasDonadas();
    }



}
