package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "registro_persona_vulnerable")
@SuperBuilder
public class RegistroPersonaVulnerable extends Persistente implements Contribucion{

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_colaborador", referencedColumnName = "id")
    private ColaboradorHumano colaborador;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tarjeta", referencedColumnName = "id")
    private TarjetaPersonaVulnerable tarjetaRepartida;

    public static RegistroPersonaVulnerable of(TarjetaPersonaVulnerable tarjetaRepartida, ColaboradorHumano h) {
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
