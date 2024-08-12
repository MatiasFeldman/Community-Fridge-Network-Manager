package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaHumano;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
public class SolicitudApertura {

    private LocalDateTime fechaHoraSolicitud;
    private static Integer horasParaEjecutarAccion = 3;
    private TarjetaHumano solicitante;
    private Heladera heladera;
    private Integer cantidadDeViandas;
    private AccionSobreViandas accion;
    private LocalDateTime fechaDeExpiracion;


    // Constructor
    public SolicitudApertura(TarjetaHumano solicitante, Integer cantidadDeVianda, AccionSobreViandas accion, Heladera heladera) {
        this.fechaHoraSolicitud = LocalDateTime.now();
        this.solicitante = solicitante;
        this.cantidadDeViandas = cantidadDeVianda;
        this.accion = accion;
        this.heladera = heladera;
        this.fechaDeExpiracion = fechaHoraSolicitud.plusHours(horasParaEjecutarAccion);
    }

    public UUID getIdHeladera(){
        return heladera.getId();
    }

    public String getIdSolicitante(){
        return solicitante.getId();
    }

    public boolean isDentroDeTiempo() {
        return LocalDateTime.now().isBefore(fechaHoraSolicitud.plusHours(horasParaEjecutarAccion));
    }


}
