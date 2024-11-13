package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import lombok.*;

import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Table(name = "tarjeta_humano")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TarjetaColaborador extends Persistente {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_humano", referencedColumnName = "id")
    private ColaboradorHumano duenio;

    private Boolean principal;



    public Long getDuenioId() {
        return this.duenio.getIdUsuario();
    }

}
