package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import lombok.*;

import java.time.LocalDateTime;



public class TarjetaHumano implements Tarjeta{
    @Setter
    private Long id;
    @Getter
    @Setter
    private Humano duenio;

    // Constructor
    public TarjetaHumano() {
        this.id = null;
        this.duenio = null;
    }

    @Override
    public void usarEn(Heladera heladera){
        heladera.verificarAcceso(this.id, LocalDateTime.now());
    }

    @Override
    public Long getId(){
        return this.id;
    }

    @Override
    public Long getDuenioId() {
        return this.duenio.getIdUsuario();
    }

}
