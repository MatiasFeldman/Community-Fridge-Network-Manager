package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


public class TarjetaHumano implements Tarjeta{
    private String id;
    @Getter
    @Setter
    private Humano duenio;

    // Constructor
    public TarjetaHumano() {
        this.id = UUID.randomUUID().toString();
    }
    @Override
    public void usarEn(Heladera heladera){
        heladera.verificarAcceso(this.id, LocalDateTime.now());
    }

    @Override
    public String getId(){
        return this.id;
    }

}
