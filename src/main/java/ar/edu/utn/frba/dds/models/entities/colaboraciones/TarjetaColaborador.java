package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "tarjeta_humano")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class TarjetaColaborador extends Persistente {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_humano", referencedColumnName = "id")
    private ColaboradorHumano duenio;

    private Boolean principal;

    public static TarjetaColaborador create(Long id, ColaboradorHumano duenio, Boolean principal) {
        return TarjetaColaborador
                .builder()
                .id(id)
                .duenio(duenio)
                .principal(principal)
                .build();
    }

    public static TarjetaColaborador create(ColaboradorHumano duenio, Boolean principal) {
        return TarjetaColaborador
                .builder()
                .duenio(duenio)
                .principal(principal)
                .presente(true)
                .build();
    }




    public Long getDuenioId() {
        return this.duenio.getIdUsuario();
    }

}
