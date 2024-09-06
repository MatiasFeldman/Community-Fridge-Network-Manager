package ar.edu.utn.frba.dds.dtos.heladeras;

import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
public class HeladeraDTO {
    @Setter
    private Direccion direccion;
    private Integer capacidadMaxima;
    @Setter
    private Integer cantActual;
    private LocalDate fechaDePuestaEnFuncionamiento;
    @Setter
    private Boolean activa;
    @Setter
    private Double ultimaTemperaturaRegistrada;
    private Double tempMinima;
    private Double tempMaxima;
    @Setter
    private Boolean hayMovimiento;
}
