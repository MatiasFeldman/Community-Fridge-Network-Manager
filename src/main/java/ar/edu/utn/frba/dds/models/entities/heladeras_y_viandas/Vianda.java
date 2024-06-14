package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import java.time.LocalDate;

public class Vianda {
    private Comida comida;
    private LocalDate fechaVencimiento;
    private LocalDate fechaDeDonacion;
    private Heladera heladeraDondeSeEncuentra;
    private Float calorias;
    private Float peso;
    private boolean entregada;
}
