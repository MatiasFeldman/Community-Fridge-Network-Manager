package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.personas.PersonaVulnerable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;
@Getter
@Entity
@Table(name = "tarjeta_persona_vulnerable")
@NoArgsConstructor
@AllArgsConstructor
public class TarjetaPersonaVulnerable extends Tarjeta{

    @Setter
    @OneToOne(mappedBy = "persona_vulnerable")
    private PersonaVulnerable duenio;

    @OneToMany(mappedBy = "uso_de_tarjeta_vulnerable")
    private List<UsoTarjeta> historialDeUsos;



    private Integer usosDeHoy(){
        LocalDate hoy = LocalDate.now();
        return Math.toIntExact(historialDeUsos.stream().filter(uso -> uso.getFecha().isEqual(hoy)).count());
    }

    private Integer usosDisponibles(){
        return 4 + duenio.getMenoresACargo() - usosDeHoy();
    }

    @Override
    public void usarEn(Heladera heladera){
        heladera.quitarViandas(1);
        historialDeUsos.add(new UsoTarjeta(heladera, LocalDate.now()));
    }

    @Override
    public Long getDuenioId() {
        return this.duenio.getId();
    }
}
