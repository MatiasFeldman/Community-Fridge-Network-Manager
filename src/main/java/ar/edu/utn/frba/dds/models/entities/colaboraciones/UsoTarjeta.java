package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "uso_de_tarjeta_vulnerable")
public class UsoTarjeta extends Persistente {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_heladera", nullable = false, referencedColumnName = "id")
    private Heladera heladera;

    @Column(name = "fecha_uso", nullable = false)
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tarjeta", referencedColumnName = "id_tarjeta", nullable = false)
    private TarjetaPersonaVulnerable tarjeta;

    public UsoTarjeta(Heladera heladera, LocalDate fecha) {
        this.heladera = heladera;
        this.fecha = fecha;
    }
}
