package ar.edu.utn.frba.dds.dtos.incidentes;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.TipoEvento;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class IncidenteDTO {
    private LocalDateTime fecha;
    private Heladera heladera;
    private TipoEvento tipo;
    private Usuario colaborador;
    private String descripcion;
    private String foto;
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

    public IncidenteDTO(LocalDateTime fecha, Heladera heladera, TipoEvento tipo, Usuario colaborador, String descripcion, String foto) {
        this.fecha = fecha;
        this.heladera = heladera;
        this.tipo = tipo;
        this.colaborador = colaborador;
        this.descripcion = descripcion;
        this.foto = foto;
        this.resuelto = false;
    }
}
