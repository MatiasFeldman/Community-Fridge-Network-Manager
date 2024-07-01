package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import ar.edu.utn.frba.dds.dtos.heladeras.HeladeraDTO;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Getter
@Builder
public class Heladera {
    @Setter
    private Coordenada coordenada;
    @Setter
    private Direccion direccion;
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
    private UUID id;

    public static Heladera of(HeladeraDTO dto){
        return Heladera
                .builder()
                .coordenada(dto.getCoordenada())
                .direccion(dto.getDireccion())
                .capacidadMaxima(dto.getCapacidadMaxima())
                .capacidadActual(dto.getCapacidadActual())
                .fechaDePuestaEnFuncionamiento(dto.getFechaDePuestaEnFuncionamiento())
                .activa(dto.isActiva())
                .ultimaTemperaturaRegistrada(dto.getUltimaTemperaturaRegistrada())
                .tempMinima(dto.getTempMinima())
                .tempMaxima(dto.getTempMaxima())
                .hayMovimiento(dto.isHayMovimiento())
                .build();
    }

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

}
