package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@AllArgsConstructor
public class Vianda {
    private Comida comida;
    private LocalDate fechaVencimiento;
    private LocalDate fechaDeDonacion;
    private Heladera heladeraDondeSeEncuentra;
    private Float calorias;
    private Float peso;
    @Setter
    private boolean entregada;
}
