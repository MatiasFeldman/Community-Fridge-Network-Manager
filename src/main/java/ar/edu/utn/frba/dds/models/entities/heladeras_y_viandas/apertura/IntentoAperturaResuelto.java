package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
public class IntentoAperturaResuelto {
    private String idTarjeta;
    private UUID idHeladera;
    private LocalDateTime fecha;
    private boolean exitoso;
}
