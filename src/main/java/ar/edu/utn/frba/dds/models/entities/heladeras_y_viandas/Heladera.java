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

    private static final String BROKER_URL = "";

    @Transient
    private List<SolicitudApertura> solicitudes; // x ahora, si debe ser persistido

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SuscripcionAHeladera> suscriptores;


    @Transient
    private Accionador accionadorParaTemperatura;
    @Transient
    private Accionador accionadorParaMovimiento;


    @Transient
    private MqttClient client_solicitudes;
    @Transient
    private MqttClient client_intentos;
    @Transient
    private static String topic_solicitudes = "heladeras/solicitudes_de_apertura";
    @Transient
    private static String topic_intentos = "heladeras/intentos_de_apertura";


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
                .tempMaxima(dto.getTempMaxima());

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
        Integer resultado = this.getCapActual() + cantidad;
        if (resultado > this.getCapacidadMaxima()) {
            throw new EspacioInsuficienteException("La heladera no tiene suficiente espacio para esa cantidad de viandas");
        } else {
            this.setCapActual(resultado);
            this.notificarColaboradores();
        }
    }

    public void quitarViandas(Integer cantidad) {
        Integer resultado = this.getCapActual() - cantidad;
        if (resultado > this.getCapActual()) {
            throw new EspacioInsuficienteException("No podes quitar esa cantidad de viandas");
        } else {
            this.setCapActual(resultado);
            this.notificarColaboradores();
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
        solicitudes.add(soliApertura);
    }

    @SneakyThrows
    public void verificarAcceso(TarjetaColaborador tarjeta, LocalDateTime fecha) {
        Optional<SolicitudApertura> aviso = this.buscarSolicitud(tarjeta);
        IntentoAperturaResuelto intento;
        ObjectMapper mapper = new ObjectMapper();
        String jsonMensaje;
        MqttMessage message;
        if (aviso.isPresent()) {
            SolicitudApertura aviso_posta = aviso.get();
            solicitudes.remove(aviso_posta);
            if (aviso_posta.getFechaDeExpiracion().isBefore(fecha)) {
                intento = new IntentoAperturaResuelto(tarjeta, this, fecha, false);

                jsonMensaje = mapper.writeValueAsString(intento);
                message = new MqttMessage(jsonMensaje.getBytes());
                message.setQos(1);

                client_intentos.publish(topic_intentos, message);
                throw new AccesoDenegadoHeladeraException("La solicitud de ingreso ya venció");
            } else {
                intento = new IntentoAperturaResuelto(tarjeta, this, fecha, true);
            }
        } else {
            intento = new IntentoAperturaResuelto(tarjeta, this, fecha, false);

            jsonMensaje = mapper.writeValueAsString(intento);
            message = new MqttMessage(jsonMensaje.getBytes());
            message.setQos(1);

            client_intentos.publish(topic_intentos, message);
            throw new AccesoDenegadoHeladeraException("No se encontró la solicitud de ingreso");
        }
        jsonMensaje = mapper.writeValueAsString(intento);

        message = new MqttMessage(jsonMensaje.getBytes());
        message.setQos(1);

        client_intentos.publish(topic_intentos, message);

    }

    public Optional<SolicitudApertura> buscarSolicitud(TarjetaColaborador tarjeta) {
        return solicitudes.stream().filter(soli -> soli.getIdTarjeta().equals(tarjeta.getId())).findFirst();
    }

    public void intentoAcceso(Object solicitud) {
        IntentoApertura intento = (IntentoApertura) solicitud;
        this.verificarAcceso(intento.getSolicitante(), intento.getFechaHoraDeIntento());
    }


    public boolean temperaturaValida(Double temp) {
        return temp >= this.tempMinima && temp <= this.tempMaxima;
    }

    public void evaluarTemperatura(Double temp) {
        this.ultimaTemperaturaRegistrada = temp;
        this.ultFechaRegistrada = LocalDateTime.now();
        if (!this.temperaturaValida(temp)) {
            this.accionadorParaTemperatura.sucedeIncidente(TipoEvento.TEMPERATURA, LocalDateTime.now(), this);
        }
    }

    public void evaluarConexion() {
        if (this.ultFechaRegistrada.plusMinutes(5).isBefore(LocalDateTime.now())) {
            this.accionadorParaTemperatura.sucedeIncidente(TipoEvento.FALLA_CONEXION, LocalDateTime.now(), this);
        }
    }

    public void hayMovimiento() {
        this.activa = false;
        this.accionadorParaMovimiento.sucedeIncidente(TipoEvento.MOVIMIENTO, LocalDateTime.now(), this);

    }

    public String direccionCompleta(){
        return this.direccion.getCalle().getNombre() + " " + this.direccion.getAltura();
    }

    public Integer cantActual() {
        return this.capacidadMaxima - this.capActual;
    }

    public Coordenada getCoordenada() {
        return this.direccion.getCoordenadas();
    }
}