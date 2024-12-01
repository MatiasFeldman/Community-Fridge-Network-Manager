package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;


import ar.edu.utn.frba.dds.dtos.heladeras.HeladeraDTO;
import ar.edu.utn.frba.dds.exceptions.EspacioInsuficienteException;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaColaborador;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.IntentoApertura;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.IntentoAperturaResuelto;
import ar.edu.utn.frba.dds.exceptions.AccesoDenegadoHeladeraException;
import ar.edu.utn.frba.dds.models.entities.persistencia.Persistente;
import ar.edu.utn.frba.dds.models.entities.suscripciones.SuscripcionAHeladera;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.*;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Table(name = "heladera")
public class Heladera extends Persistente {

    @Column(name = "nombre")
    private String nombre;

    @Embedded
    private Direccion direccion;

    @Column(name = "capacidad_maxima")
    private Integer capacidadMaxima;

    @Column(name = "capacidad_actual")
    private Integer capActual;

    @Column(name = "fecha_alta")
    private LocalDate fechaDePuestaEnFuncionamiento;

    @Column(name = "esta_activa")
    private Boolean activa;

    @Column(name = "temperatura_minima")
    private Double tempMinima;

    @Column(name = "temperatura_maxima")
    private Double tempMaxima;

    @Column(name = "ultima_temperatura_registrada")
    private Double ultimaTemperaturaRegistrada;

    @Column(name = "ultima_fecha_registrada")
    private LocalDateTime ultFechaRegistrada;

    @Column(name = "viandas_colocadas")
    private Integer viandasColocadas;

    @Column(name = "viandas_retiradas")
    private Integer viandasRetiradas;

    private static final String BROKER_URL = "tcp://broker.hivemq.com:1883";

    @OneToMany(mappedBy = "heladera", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SolicitudApertura> solicitudes = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SuscripcionAHeladera> suscriptores;



    @Transient
    private MqttClient client_solicitudes;
    @Transient
    private MqttClient client_intentos;
    @Transient
    private static String topic_solicitudes = "heladeras/solicitudes_de_apertura";
    @Transient
    private static String topic_intentos = "heladeras/intentos_de_apertura";

    @Setter
    @Transient
    private String mensajeDisponiblididad;


    @SneakyThrows
    public static Heladera of(HeladeraDTO dto) {
        HeladeraBuilder builder = Heladera
                .builder()
                .direccion(dto.getDireccion())
                .capacidadMaxima(dto.getCapacidadMaxima())
                .capActual(dto.getCantActual())
                .fechaDePuestaEnFuncionamiento(dto.getFechaDePuestaEnFuncionamiento())
                .activa(dto.getActiva())
                .ultimaTemperaturaRegistrada(dto.getUltimaTemperaturaRegistrada())
                .tempMinima(dto.getTempMinima())
                .tempMaxima(dto.getTempMaxima())
                .viandasColocadas(0)
                .viandasRetiradas(0);

        MqttClient client1 = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client1.subscribe(topic_solicitudes);

        MqttClient client2 = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client2.connect();

        builder.client_solicitudes(client1);
        return builder.build();
    }

    public static Heladera of(String punto) {
        return Heladera
                .builder()
                .nombre(punto)
                .viandasColocadas(0)
                .viandasRetiradas(0)
                .suscriptores(new ArrayList<>())
                .solicitudes(new ArrayList<>())
                .build();
    }



    public void suscribir(SuscripcionAHeladera suscripcion) {
        suscriptores.add(suscripcion);
    }

    public void desuscribir(SuscripcionAHeladera suscripcion) {
        suscriptores.remove(suscripcion);
    }

    public void notificarColaboradores() {
        suscriptores.forEach(s -> s.notificar(this.capActual, this.cantActual()));
    }

    public void notificarFallaTecnica() {
        suscriptores.forEach(s -> s.notificar(-1, -1));
    }

    public void agregarViandas(Integer cantidad) {
        if (cantidad > this.getCapActual()) {
            throw new EspacioInsuficienteException("La heladera no tiene suficiente espacio para esa cantidad de viandas");
        } else {
            this.setCapActual(this.getCapActual() - cantidad);
            this.notificarColaboradores();
            this.viandasColocadas += cantidad;
            System.out.println("Heladeras colocadas en la ultima semana en:" + this.getId() + " - " + viandasColocadas);
        }
    }

    public void quitarViandas(Integer cantidad) {
        Integer resultado = this.getCapActual() + cantidad;
        if (cantidad > this.getCapActual()) {
            throw new EspacioInsuficienteException("No podes quitar esa cantidad de viandas");
        } else {
            this.setCapActual(resultado);
            this.notificarColaboradores();
            this.viandasRetiradas += cantidad;
            System.out.println("Heladeras retiradas en la ultima semana en: " + this.getId() + " - " + viandasRetiradas);
        }

    }

    public Integer mesesActiva() {
        return Math.toIntExact(ChronoUnit.MONTHS.between(this.fechaDePuestaEnFuncionamiento, LocalDate.now()));
    }

    public void desactivar() {
        this.setActiva(false);
    }

    public void activar() {
        this.setActiva(true);
    }

    public void agregarSolicitudApertura(SolicitudApertura soliApertura) {
        System.out.println("Solicitud de apertura recibida en la heladera " + this.getId());
        System.out.println("Solicitante: " + soliApertura.getColaboradorHumano().getId());
        System.out.println("Cantidad de viandas: " + soliApertura.getCantidadDeViandas());
        System.out.println("Tarjeta autorizada: " + soliApertura.getIdTarjeta());
        System.out.println("Fecha de expiracion: " + soliApertura.getFechaDeExpiracion());


        solicitudes.add(soliApertura);
    }


    public Boolean tieneAcceso(Long idTarjeta) {
        return solicitudes.stream().anyMatch(soli -> soli.getIdTarjeta().equals(idTarjeta));
    }

    public Optional<SolicitudApertura> buscarSolicitud(Long idColab) {
        return solicitudes.stream().filter(soli -> soli.getIdTarjeta().equals(idColab)).findFirst();
    }



    public boolean temperaturaValida(Double temp) {
        return temp >= this.tempMinima && temp <= this.tempMaxima;
    }

    public void evaluarTemperatura(Double temp) {
        this.ultimaTemperaturaRegistrada = temp;
        this.ultFechaRegistrada = LocalDateTime.now();

        if (!this.temperaturaValida(temp)) {
            System.out.println("Temperatura fuera de rango en la heladera " + this.getId());
            ServiceLocator.instanceOf(Accionador.class).sucedeIncidente(TipoEvento.TEMPERATURA, LocalDateTime.now(), this);
            System.out.println("Temperatura fuera de rango en la healdera " + this.getId());
        }

    }

    public void evaluarConexion() {
        if (this.ultFechaRegistrada.plusMinutes(5).isBefore(LocalDateTime.now())) {
            ServiceLocator.instanceOf(Accionador.class).sucedeIncidente(TipoEvento.FALLA_CONEXION, LocalDateTime.now(), this);
        }
    }

    public void hayMovimiento() {
        this.activa = false;
        System.out.println("Se detectó movimiento en la heladera " + this.nombre);
        ServiceLocator.instanceOf(Accionador.class).sucedeIncidente(TipoEvento.MOVIMIENTO, LocalDateTime.now(), this);

    }

    public String direccionCompleta(){
        return this.direccion.getDireccion();
    }

    public Integer cantActual() {
        return this.capacidadMaxima - this.capActual;
    }

    public Coordenada getCoordenada() {
        return this.direccion.getCoordenadas();
    }

    public void eliminarSolicitud(Long idTarjeta) {
        solicitudes.stream()
                .filter(soli -> soli.getIdTarjeta().equals(idTarjeta))
                .findFirst()
                .ifPresent(solicitudes::remove);
    }
}