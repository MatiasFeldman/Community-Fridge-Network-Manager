package ar.edu.utn.frba.dds.models.entities.tecnicos;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import lombok.Builder;

import java.awt.*;
import java.time.LocalDateTime;


@Builder
public class VisitaAHeladera {
    private Tecnico tecnico;
    private Heladera heladeraFallada;
    private LocalDateTime fechaDeVisita;
    private boolean solucionado;
    private String descripcion;
    private Image foto = null;


    public static VisitaAHeladera crear(Tecnico tecnico, Heladera heladeraFallada, LocalDateTime fechaDeVisita, boolean solucionado, String descripcion, Image foto){
        return VisitaAHeladera
                .builder()
                .tecnico(tecnico)
                .heladeraFallada(heladeraFallada)
                .fechaDeVisita(fechaDeVisita)
                .solucionado(solucionado)
                .foto(foto)
                .descripcion(descripcion)
                .build();
    }

    public static VisitaAHeladera crear(Tecnico tecnico, Heladera heladeraFallada, LocalDateTime fechaDeVisita, boolean solucionado, String descripcion){
        return VisitaAHeladera
                .builder()
                .tecnico(tecnico)
                .heladeraFallada(heladeraFallada)
                .fechaDeVisita(fechaDeVisita)
                .solucionado(solucionado)
                .descripcion(descripcion)
                .build();
    }


}
