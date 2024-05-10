package ar.edu.utn.frba.dds.colaboraciones;

import ar.edu.utn.frba.dds.Heladeras_Y_Viandas.Heladera;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class UsoTarjeta {
    private Heladera heladera;
    private LocalDate fecha;
}
