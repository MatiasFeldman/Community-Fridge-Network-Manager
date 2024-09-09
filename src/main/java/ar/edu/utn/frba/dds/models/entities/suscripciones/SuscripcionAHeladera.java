package ar.edu.utn.frba.dds.models.entities.suscripciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.suscripciones.tipo_suscripciones.Suscripcion;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "suscriptor_heladera")
public class SuscripcionAHeladera {
    @Id
    @GeneratedValue
    @Column(name = "id_suscriptor")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Usuario observerSuscripcion;

    @ManyToOne
    @JoinColumn(name = "id_suscripcion", referencedColumnName = "id_suscripcion")
    private Suscripcion suscripcion;

    @ManyToOne
    @JoinColumn(name = "heladera_id")
    private Heladera heladera;


    @SneakyThrows
    public void notificar(Integer capActual, Integer cantActual) {
        if (suscripcion.verificarCondicion(capActual, cantActual)){
            observerSuscripcion.serNotificado(suscripcion.getMensaje());
        }
    }

    public SuscripcionAHeladera(Usuario observerSuscripcion, Suscripcion suscripcion) {
        this.observerSuscripcion = observerSuscripcion;
        this.suscripcion = suscripcion;
    }
}
