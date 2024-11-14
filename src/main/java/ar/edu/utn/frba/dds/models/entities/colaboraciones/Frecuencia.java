package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@Getter
public class Frecuencia {
    private ChronoUnit unidad;
    private Integer frecuencia;
    @Setter
    private LocalDate fechaUltimaDonacion;

    public boolean pasoElTiempo(){
        return fechaUltimaDonacion.plus(frecuencia,unidad).isBefore(LocalDate.now());
    }

    public long vecesCumplidas() {
        LocalDate fechaInicio = fechaUltimaDonacion;
        LocalDate fechaActual = LocalDate.now();

        long vecesCumplidas = 1;
        while (fechaInicio.isBefore(fechaActual)) {
            fechaInicio = fechaInicio.plus(frecuencia, unidad);
            if (fechaInicio.isBefore(fechaActual) || fechaInicio.isEqual(fechaActual)) {
                vecesCumplidas++;
            }
        }
        return vecesCumplidas;
    }
}
