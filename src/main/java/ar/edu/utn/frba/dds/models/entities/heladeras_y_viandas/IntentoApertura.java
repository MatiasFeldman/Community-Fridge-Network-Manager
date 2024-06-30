package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import ar.edu.utn.frba.dds.models.entities.personas.Humano;

import java.time.LocalDateTime;

public class IntentoApertura {
    private LocalDateTime FechaHoraDeIntento;
    private Humano persona;
    private boolean acceso;

    public IntentoApertura(Humano persona, boolean pudoAcceder) {
        this.FechaHoraDeIntento = LocalDateTime.now(); // Asigna la fecha y hora actuales
        this.persona = persona;
        this.acceso = pudoAcceder;
    }
}
