package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import lombok.*;

import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Table(name = "tarjeta_humano")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TarjetaColaborador extends Tarjeta{

    @ManyToOne
    @JoinColumn(name = "id_humano", referencedColumnName = "id_humano")
    private ColaboradorHumano duenio;

    @Override
    public void usarEn(Heladera heladera){
        heladera.verificarAcceso(this, LocalDateTime.now());
    }

    @Override
    public Long getDuenioId() {
        return this.duenio.getIdUsuario();
    }

}
