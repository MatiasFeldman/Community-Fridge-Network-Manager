package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import java.time.LocalDateTime;

public class ReceptorMovimiento {
    private Accionador accionadorParaMovimiento;

    public void evaluar(boolean movimiento){
        if (movimiento){
            accionadorParaMovimiento.sucedeIncidente(TipoEvento.MOVIMIENTO, LocalDateTime.now());
        }
    }
}
