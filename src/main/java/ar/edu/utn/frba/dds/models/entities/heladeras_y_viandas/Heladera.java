package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
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
    private double ultimaTemperaturaRegistrada;
    private double tempMinima;
    private double tempMaxima;
    @Setter
    private boolean hayMovimiento;


    public void agregarViandas(Integer cantidad){
        this.setCapacidadActual(this.getCapacidadActual() - cantidad);
    }

    public void quitarViandas(Integer cantidad){
        this.setCapacidadActual(this.getCapacidadActual() + cantidad);
    }

    public Integer mesesActiva(){
        return Math.toIntExact(ChronoUnit.MONTHS.between(this.fechaDePuestaEnFuncionamiento, LocalDate.now()));
    }

    public void desactivar(){
        this.setActiva(false);
    }

    public void activar(){
        this.setActiva(true);
    }

    public void recibirTemperatura(){
        //Simula recibir la temperatura de la heladera cada 5 mins
        this.setUltimaTemperaturaRegistrada(10);
        if (!this.estaEntreLosLimites(ultimaTemperaturaRegistrada)){
            this.desactivar();
        }
    } // pendiente entrega

    public void recibirMovimiento(){
        //Simula recibir si hay movimiento
        this.setHayMovimiento(true);
        this.desactivar();
    }

    private boolean estaEntreLosLimites(double temp) {
        return temp >= this.tempMinima && temp <= this.tempMaxima;
    }


}
