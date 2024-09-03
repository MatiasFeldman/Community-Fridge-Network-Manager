package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
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
public class UsoTarjeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_uso")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_heladera", nullable = false)
    private Heladera heladera;

    @Column(name = "fecha_uso", nullable = false)
    private LocalDate fecha;

    public UsoTarjeta(Heladera heladera, LocalDate fecha) {
        this.heladera = heladera;
        this.fecha = fecha;
    }
}
