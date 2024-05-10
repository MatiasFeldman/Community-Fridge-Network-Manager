package ar.edu.utn.frba.dds.Heladeras_Y_Viandas;

import ar.edu.utn.frba.dds.ubicacion.Coordenada;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
public class Heladera {
    @Setter
    private Coordenada coordenada;
    @Setter
    private PuntoDeHeladera nombre;
    private Integer capacidadMaxima;
    @Setter
    private Integer capacidadActual;
    private LocalDate fechaDePuestaEnFuncionamiento;
    @Setter
    private boolean activa;
    @Setter
    private Float ultimaTemperaturaRegistrada;
    private Float tempMinima;
    private Float tempMaxima;
    @Setter
    private boolean hayMovimiento;

    public void agregarViandas(Integer cantidad){
        this.setCapacidadActual(this.getCapacidadActual() - cantidad);
    }

    public void quitarViandas(Integer cantidad){
        this.setCapacidadActual(this.getCapacidadActual() + cantidad);
    }

    public long mesesActiva(){
        return ChronoUnit.MONTHS.between(this.fechaDePuestaEnFuncionamiento, LocalDate.now());
    }

    public void desactivar(){
        this.setActiva(false);
    }

    public void activar(){
        this.setActiva(true);
    }

    public void intentoDeRobo(){

    }


}
