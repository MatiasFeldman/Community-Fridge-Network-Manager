package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaColaborador;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "intento_apertura_resuelto")
public class IntentoAperturaResuelto extends Persistente {

    @ManyToOne
    @JoinColumn(name = "id_tarjeta", referencedColumnName = "id")
    private TarjetaColaborador tarjeta;

    @ManyToOne
    @JoinColumn(name = "id_colaborador", referencedColumnName = "id")
    private ColaboradorHumano colaborador;

    @ManyToOne
    @JoinColumn(name = "id_heladera", referencedColumnName = "id")
    private Heladera heladera;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "exitoso")
    private Boolean exitoso;

    public Long getIdTarjeta(){
        return this.tarjeta.getId();
    }
}
