package ar.edu.utn.frba.dds.dtos.incidentes;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.TipoEvento;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class IncidenteDTO {
    private LocalDateTime fecha;
    private Heladera heladera;
    private TipoEvento tipo;
    private Object colaborador;
    private String descripcion;
    private Image foto;
    private boolean resuelto;

    public IncidenteDTO(LocalDateTime fecha, Heladera heladera, TipoEvento tipo) {
        this.fecha = fecha;
        this.heladera = heladera;
        this.tipo = tipo;
        this.colaborador = null;
        this.descripcion = null;
        this.foto = null;
        this.resuelto = false;
    }

    public IncidenteDTO(LocalDateTime fecha, Heladera heladera, TipoEvento tipo, Object colaborador, String descripcion, Image foto) {
        this.fecha = fecha;
        this.heladera = heladera;
        this.tipo = tipo;
        this.colaborador = colaborador;
        this.descripcion = descripcion;
        this.foto = foto;
        this.resuelto = false;
    }
}
