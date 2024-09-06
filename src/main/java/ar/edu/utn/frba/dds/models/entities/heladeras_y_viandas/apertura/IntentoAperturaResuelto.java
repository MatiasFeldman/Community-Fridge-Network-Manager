package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaHumano;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "intento_apertura_resuelto")
public class IntentoAperturaResuelto {
    @Id
    @GeneratedValue
    @Column(name = "id_intento")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_tarjeta", referencedColumnName = "id_tarjeta")
    private TarjetaHumano tarjeta;

    @ManyToOne
    @JoinColumn(name = "id_heladera", referencedColumnName = "id_heladera")
    private Heladera heladera;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "exitoso")
    private Boolean exitoso;

    public IntentoAperturaResuelto(TarjetaHumano idTarjeta, Heladera idHeladera, LocalDateTime fecha, Boolean exitoso) {
        this.tarjeta = idTarjeta;
        this.heladera = idHeladera;
        this.fecha = fecha;
        this.exitoso = exitoso;
    }

    public Long getIdTarjeta(){
        return this.tarjeta.getId();
    }
}
