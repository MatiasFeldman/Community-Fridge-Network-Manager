package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;


import ar.edu.utn.frba.dds.converter.DireccionConverter;
import ar.edu.utn.frba.dds.converter.PuntoDeHeladeraConverter;
import ar.edu.utn.frba.dds.dtos.heladeras.HeladeraDTO;
import ar.edu.utn.frba.dds.exceptions.EspacioInsuficienteException;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.IntentoApertura;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.IntentoAperturaResuelto;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.sensores_y_receptores.ReceptorMovimiento;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.sensores_y_receptores.ReceptorTemperatura;
import ar.edu.utn.frba.dds.exceptions.AccesoDenegadoHeladeraException;
import ar.edu.utn.frba.dds.models.entities.suscripciones.SuscripcionAHeladera;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Table(name = "heladera")
public class Heladera implements IMqttMessageListener {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_heladera")
    private Long id;

    @Convert(converter = PuntoDeHeladeraConverter.class)
    @Column(name = "nombre")
    private PuntoDeHeladera nombre;

    @Transient
    private Coordenada coordenada; // la direccion ya tiene a la coordenada

    @Setter
    @Convert(converter = DireccionConverter.class)
    @Column(name = "direccion")
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

    @Transient
    private List<SolicitudApertura> solicitudes; // x ahora, si debe ser persistido

    @Transient
    private List<SuscripcionAHeladera> suscriptores; // x ahora, si debe ser persistido

    @Transient
    private ReceptorTemperatura receptorTemperatura;
    @Transient
    private ReceptorMovimiento receptorMovimiento;
    @Transient
    private MqttReceptorApertura receptorApertura;
    @Transient
    private Double ultimaTemperaturaRegistrada;
    @Transient
    private Boolean hayMovimiento;
    @Transient
    private Accionador accionadorParaTemperatura;
    @Transient
    private Accionador accionadorParaMovimiento;

    @Transient
    private static String BROKER_URL;
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
                .coordenada(dto.getCoordenada())
                .direccion(dto.getDireccion())
                .capacidadMaxima(dto.getCapacidadMaxima())
                .capActual(dto.getCantActual())
                .fechaDePuestaEnFuncionamiento(dto.getFechaDePuestaEnFuncionamiento())
                .activa(dto.isActiva())
                .ultimaTemperaturaRegistrada(dto.getUltimaTemperaturaRegistrada())
                .tempMinima(dto.getTempMinima())
                .tempMaxima(dto.getTempMaxima())
                .hayMovimiento(dto.isHayMovimiento());

        MqttClient client1 = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client1.subscribe(topic_solicitudes);

        MqttClient client2 = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client2.connect();

        builder.client_solicitudes(client1);
        return builder.build();
    }

    public static Heladera of(PuntoDeHeladera punto){
        return Heladera
                .builder()
                .nombre(punto)
                .suscriptores(new ArrayList<>())
                .solicitudes(new ArrayList<>())
                .build();
    }

    public String nombrePunto(){
        return this.nombre.getNombreDePunto();
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

    public void notificarFallaTecnica(){
        suscriptores.forEach(s -> s.notificar(-1, -1));
    }

    public void agregarViandas(Integer cantidad) {
        Integer resultado = this.getCapActual() + cantidad;
        if (resultado > this.getCapacidadMaxima()){
            throw new EspacioInsuficienteException("La heladera no tiene suficiente espacio para esa cantidad de viandas");
        } else{
            this.setCapActual(resultado);
            this.notificarColaboradores();
        }
    }

    public void quitarViandas(Integer cantidad) {
        Integer resultado = this.getCapActual() - cantidad;
        if (resultado > this.getCapActual()){
            throw new EspacioInsuficienteException("No podes quitar esa cantidad de viandas");
        } else{
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
    public void verificarAcceso(Long id, LocalDateTime fecha) {
        Optional<SolicitudApertura> aviso = solicitudes.stream().filter(soli -> soli.getIdTarjeta().equals(id)).findFirst();
        IntentoAperturaResuelto intento;
        ObjectMapper mapper = new ObjectMapper();
        String jsonMensaje;
        MqttMessage message;
        if (aviso.isPresent()) {
            SolicitudApertura aviso_posta = aviso.get();
            solicitudes.remove(aviso_posta);
            if (aviso_posta.getFechaDeExpiracion().isBefore(fecha)) {
                intento = new IntentoAperturaResuelto(id, this.id, fecha, false);

                jsonMensaje = mapper.writeValueAsString(intento);
                message = new MqttMessage(jsonMensaje.getBytes());
                message.setQos(1);

                client_intentos.publish(topic_intentos, message);
                throw new AccesoDenegadoHeladeraException("La solicitud de ingreso ya venció");
            } else {
                intento = new IntentoAperturaResuelto(id, this.id, fecha, true);
            }
        } else{
            intento = new IntentoAperturaResuelto(id, this.id, fecha, false);

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

    public void intentoAcceso(Object solicitud) {
        IntentoApertura intento = (IntentoApertura) solicitud;
        this.verificarAcceso(intento.getIdTarjeta(), intento.getFechaHoraDeIntento());
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        String jsonMensaje = mqttMessage.toString();
        ObjectMapper mapper = new ObjectMapper();
        SolicitudApertura msg = mapper.readValue(jsonMensaje, SolicitudApertura.class);

        if (msg.getHeladera().getId().equals(this.id)) {
            this.agregarSolicitudApertura(msg);
        }
    }

    public boolean temperaturaValida(Double temp){
        return temp >= this.tempMinima && temp <= this.tempMaxima;
    }

    public void evaluarTemperatura(){
        Double ultimaTemp = this.receptorTemperatura.getTemperaturaRegistrada();
        if (!this.temperaturaValida(ultimaTemp)){
            this.accionadorParaTemperatura.sucedeIncidente(TipoEvento.TEMPERATURA, LocalDateTime.now(), this);
        }
    }

    public void evaluarConexion(){
        LocalDateTime ultFecha = this.receptorTemperatura.getUltFechaRegistrada();
        if (ultFecha.plusMinutes(5).isBefore(LocalDateTime.now())){
            this.accionadorParaTemperatura.sucedeIncidente(TipoEvento.FALLA_CONEXION, LocalDateTime.now(), this);
        }
    }

    public void evaluarMovimiento(){
        if (this.receptorMovimiento.isMovimiento()){
            this.accionadorParaMovimiento.sucedeIncidente(TipoEvento.MOVIMIENTO, LocalDateTime.now(), this);
        }
    }

    public Integer cantActual(){
        return this.capacidadMaxima - this.capActual;
    }

}

