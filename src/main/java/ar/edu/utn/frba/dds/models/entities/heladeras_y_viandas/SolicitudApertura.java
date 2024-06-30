package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaHumano;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;

import java.time.LocalDateTime;

public class SolicitudApertura {
    private LocalDateTime fechaHoraSolicitud;
    private Integer horasParaEjecutarAccion;
    private TarjetaHumano solicitante;
    private Integer cantidadDeViandas;

    // Constructor
    public SolicitudApertura(TarjetaHumano solicitante, Integer horasParaEjecutar, Integer cantidadDeVianda) {
        this.fechaHoraSolicitud = LocalDateTime.now(); // Asigna la fecha y hora actuales
        this.horasParaEjecutarAccion = horasParaEjecutar;
        this.solicitante = solicitante;
        this.cantidadDeViandas = cantidadDeVianda;
    }

    // Getters
    public LocalDateTime getFechaHoraSolicitud() {
        return fechaHoraSolicitud;
    }

    public TarjetaHumano getSolicitante() {
        return solicitante;
    }

    public boolean isDentroDeTiempo() {
        return LocalDateTime.now().isBefore(fechaHoraSolicitud.plusHours(horasParaEjecutarAccion));//TODO:revisarala
    }
}
