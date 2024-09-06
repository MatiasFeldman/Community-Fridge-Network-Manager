package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import lombok.Getter;


@Entity
@Getter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Tarjeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarjeta")
    private Long id;

    @Column(name = "tipo_tarjeta")
    @Enumerated(EnumType.STRING)
    private TipoTarjeta tipoTarjeta;

    public abstract void usarEn(Heladera heladera);


    public abstract Long getDuenioId();
}

