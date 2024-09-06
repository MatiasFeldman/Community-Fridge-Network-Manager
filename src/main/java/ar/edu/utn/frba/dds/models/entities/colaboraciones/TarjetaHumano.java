package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import lombok.*;

import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Table(name = "tarjeta_humano")
@NoArgsConstructor
@AllArgsConstructor
public class TarjetaHumano implements Tarjeta{
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarjeta")
    private Long id;

    @Getter
    @Setter
    @OneToOne(mappedBy = "humano")
    private Humano duenio;

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
