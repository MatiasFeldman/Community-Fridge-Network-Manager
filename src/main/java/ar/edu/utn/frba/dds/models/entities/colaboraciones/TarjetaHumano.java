package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.entities.personas.PersonaVulnerable;
import lombok.*;

import java.time.LocalDate;
//@Setter
@Data
@EqualsAndHashCode(of = "id")
public class TarjetaHumano implements Tarjeta{
    @Getter
    private String id;
    @Getter
    private Humano duenio;

    // Constructor
    public TarjetaHumano(String id) {
        this.id = id;
    }
    @Override
    public void usarEn(Heladera heladera){
        heladera.verificarAcceso(this);
    }
}
