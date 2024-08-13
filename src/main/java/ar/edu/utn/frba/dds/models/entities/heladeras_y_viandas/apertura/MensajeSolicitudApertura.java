package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class MensajeSolicitudApertura {
    private UUID idHeladera;
    private String idTarjeta;
    private LocalDateTime fecha; // Si es aviso es la fecha de expiracion y si es intento la fecha de intento

}
