package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.exceptions.TarjetasAgotadasException;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.entities.personas.PersonaVulnerable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "registro_persona_vulnerable")
@Builder
public class RegistroPersonaVulnerable implements Contribucion{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_contribucion")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_colaborador", referencedColumnName = "id_humano")
    private Humano colaborador;

    @OneToOne
    @JoinColumn(name = "id_tarjeta", referencedColumnName = "id_tarjeta")
    private TarjetaPersonaVulnerable tarjetaRepartida;

    public static RegistroPersonaVulnerable of(TarjetaPersonaVulnerable tarjetaRepartida, Humano h) {
        return RegistroPersonaVulnerable
                .builder()
                .colaborador(h)
                .tarjetaRepartida(tarjetaRepartida)
                .build();
    }

    @Override
    public Double calcularPuntaje() {
        ConstantesMultiplicativas constantes = new ConstantesMultiplicativas();
        return constantes.getCteTarjetas();
    }


}
