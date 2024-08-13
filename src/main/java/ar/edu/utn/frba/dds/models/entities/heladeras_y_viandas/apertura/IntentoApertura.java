package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaHumano;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class IntentoApertura {
    private LocalDateTime fechaHoraDeIntento;
    private TarjetaHumano solicitante;


    public String getIdTarjeta(){
        return solicitante.getId();
    }

}
