package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaHumano;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class SolicitudApertura {

    @Getter
    @Setter
    private LocalDateTime fechaHoraSolicitud;
    private Integer horasParaEjecutarAccion = 3;
    @Getter
    private TarjetaHumano solicitante;
    @Getter
    private Integer cantidadDeViandas;
    @Setter
    @Getter
    private Vianda vianda;

    // Constructor
    public SolicitudApertura(TarjetaHumano solicitante, Integer cantidadDeVianda) {
        this.fechaHoraSolicitud = LocalDateTime.now(); // Asigna la fecha y hora actuales
        this.solicitante = solicitante;
        this.cantidadDeViandas = cantidadDeVianda;
        this.vianda = null;
    }

    public boolean isDentroDeTiempo() {
        return LocalDateTime.now().isBefore(fechaHoraSolicitud.plusHours(horasParaEjecutarAccion));//TODO:revisarala
    }
}
