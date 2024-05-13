package ar.edu.utn.frba.dds.colaboraciones;

import ar.edu.utn.frba.dds.Heladeras_Y_Viandas.Heladera;
import ar.edu.utn.frba.dds.personas.PersonaVulnerable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;

public class Tarjeta {
    private String id;
    @Setter
    @Getter
    private PersonaVulnerable duenio;
    private ArrayList<UsoTarjeta> historialDeUsos;

    public Tarjeta(String id) {
        this.id = id;
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

    private void usarEn(Heladera heladera){
        heladera.quitarViandas(1);
        historialDeUsos.add(new UsoTarjeta(heladera, LocalDate.now()));
    }
}
