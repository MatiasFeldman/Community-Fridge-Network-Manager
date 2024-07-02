package ar.edu.utn.frba.dds.models.entities.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.personas.PersonaVulnerable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;
@Getter
public class TarjetaPersonaVulnerable implements Tarjeta{
    private String id;
    @Setter
    private PersonaVulnerable duenio;
    private ArrayList<UsoTarjeta> historialDeUsos;

    public TarjetaPersonaVulnerable() {
        this.id = UUID.randomUUID().toString();
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
        heladera.modificarViandas(-1);
        historialDeUsos.add(new UsoTarjeta(heladera, LocalDate.now()));
    }
}
