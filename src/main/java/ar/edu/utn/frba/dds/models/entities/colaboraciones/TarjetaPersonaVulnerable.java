package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.personas.PersonaVulnerable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;
@Getter
@Entity
@Table(name = "tarjeta_persona_vulnerable")
public class TarjetaPersonaVulnerable implements Tarjeta{
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarjeta")
    private Long id;

    @Setter
    @OneToOne
    @JoinColumn(name = "id_persona_vulnerable", referencedColumnName = "id_persona_vulnerable")
    private PersonaVulnerable duenio;

    @OneToMany(mappedBy = "TarjetaPersonaVulnerable", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UsoTarjeta> historialDeUsos;

    public TarjetaPersonaVulnerable() {
        this.id = null;
        this.duenio = null;
        this.historialDeUsos = new ArrayList<>();
    }

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
