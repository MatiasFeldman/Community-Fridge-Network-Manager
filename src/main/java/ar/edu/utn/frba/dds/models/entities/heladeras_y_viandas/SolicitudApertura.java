package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

public class SolicitudApertura {
    private LocalDateTime fechaHoraSolicitud;
    private Integer horasParaEjecutarAccion;
    private Humano solicitante;
    private Integer cantidadDeViandas;

    // Constructor
    public SolicitudApertura(Humano solicitante, Integer horasParaEjecutar, Integer cantidadDeVianda) {
        this.fechaHoraSolicitud = LocalDateTime.now(); // Asigna la fecha y hora actuales
        this.horasParaEjecutarAccion = horasParaEjecutar;
        this.solicitante = solicitante;
        this.cantidadDeViandas = cantidadDeVianda;
    }
}
