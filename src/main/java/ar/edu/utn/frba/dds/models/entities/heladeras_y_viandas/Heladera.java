package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;


import ar.edu.utn.frba.dds.dtos.heladeras.HeladeraDTO;
import ar.edu.utn.frba.dds.models.entities.suscripciones.ObserverSuscripcion;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaHumano;
import ar.edu.utn.frba.dds.exceptions.AccesoDenegadoHeladeraException;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Builder
@Setter
public class Heladera {

    private Coordenada coordenada;
    @Setter
    private Direccion direccion;
    private PuntoDeHeladera nombre;
    private Integer capacidadMaxima;
    private Integer capacidadActual;
    private LocalDate fechaDePuestaEnFuncionamiento;
    private boolean activa;
    private double ultimaTemperaturaRegistrada;
    private double tempMinima;
    private double tempMaxima;
    private boolean hayMovimiento;
    private UUID id;
    private List<SolicitudApertura> solicitudes = new ArrayList<>();
    private List<IntentoApertura> registrosAperturas = new ArrayList<>();

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

    private final List<ObserverSuscripcion> colaboradores = new ArrayList<>();

    public void suscribir(ObserverSuscripcion colaborador){
        colaboradores.add(colaborador);
    }

    public void desuscribir(ObserverSuscripcion colaborador){
        colaboradores.remove(colaborador);
    }

    public void notificarColaboradores() {
        colaboradores.forEach(colaborador -> colaborador.verificarEvento(this));
    }

    public void modificarViandas(Integer cantidad){
        this.setCapacidadActual(this.getCapacidadActual() - cantidad);
        this.notificarColaboradores();
    }

    public void quitarViandas(Integer cantidad){
        this.setCapacidadActual(this.getCapacidadActual() + cantidad);
        this.notificarColaboradores();
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


    public void agregarSolicitudApertura(SolicitudApertura soliApertura) {
        solicitudes.add(soliApertura);
    }
    public void agregarApertura(IntentoApertura intentoApertura) {
        registrosAperturas.add(intentoApertura);
    }

    public boolean verificarAcceso(TarjetaHumano tarjeta) {
        for (SolicitudApertura solicitud : solicitudes) {
            if (solicitud.getSolicitante().equals(tarjeta) && solicitud.isDentroDeTiempo() && solicitud.isAutorizado()) {
                agregarApertura(new IntentoApertura(tarjeta.getDuenio(), true) );
                this.modificarViandas(solicitud.getCantidadDeViandas());
                if(solicitud.getVianda()!=null){ solicitud.getVianda().setEntregada(true); }

                return true;
            }
        }
        agregarApertura(new IntentoApertura(tarjeta.getDuenio(), false) );
        throw new AccesoDenegadoHeladeraException("El usuario carece de permisos para realizar dicha acción");
    }

    public void actualizarSolicitud(Humano humano, boolean bool){
        Optional<SolicitudApertura> solicitud = solicitudes.stream().filter(soli->soli.getSolicitante().equals(humano)).findFirst();
        if(solicitud.isPresent()){
            solicitud.get().setAutorizado(bool);
        }
    }
}
