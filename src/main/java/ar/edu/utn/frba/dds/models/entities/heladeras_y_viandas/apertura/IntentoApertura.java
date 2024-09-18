package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaColaborador;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class IntentoApertura {
    private LocalDateTime fechaHoraDeIntento;
    private TarjetaColaborador solicitante;


    public Long getIdTarjeta(){
        return solicitante.getId();
    }

}
