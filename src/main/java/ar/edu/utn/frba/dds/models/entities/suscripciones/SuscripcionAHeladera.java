package ar.edu.utn.frba.dds.models.entities.suscripciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import ar.edu.utn.frba.dds.models.entities.suscripciones.tipo_suscripciones.Suscripcion;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.factories.sending_strategy.SendingStrategyFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "suscriptor_heladera")
public class SuscripcionAHeladera extends Persistente {

    @Getter
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario observerSuscripcion;

    @Getter
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_suscripcion", referencedColumnName = "id_suscripcion")
    private Suscripcion suscripcion;

    @Getter
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "heladera_id", referencedColumnName = "id")
    private Heladera heladera;


    @SneakyThrows
    public void notificar(Integer capActual, Integer cantActual) {
        if (suscripcion.verificarCondicion(capActual, cantActual)){
            observerSuscripcion.setStrategiaDeEnvio(SendingStrategyFactory.create("EMAIL"));
            observerSuscripcion.serNotificado(suscripcion.getMensaje());
        }
    }

}
