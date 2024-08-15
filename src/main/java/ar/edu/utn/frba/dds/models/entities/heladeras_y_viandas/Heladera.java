package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;


import ar.edu.utn.frba.dds.dtos.heladeras.HeladeraDTO;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.IntentoApertura;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.IntentoAperturaResuelto;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.MensajeSolicitudApertura;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.sensores_y_receptores.ReceptorMovimiento;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.sensores_y_receptores.ReceptorTemperatura;
import ar.edu.utn.frba.dds.models.entities.suscripciones.ObserverSuscripcion;
import ar.edu.utn.frba.dds.exceptions.AccesoDenegadoHeladeraException;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Builder
@Setter
public class Heladera implements IMqttMessageListener {

    private Coordenada coordenada;
    @Setter
    private Direccion direccion;
    private PuntoDeHeladera nombre;
    private Integer capacidadMaxima;
    private Integer capacidadActual;
    private LocalDate fechaDePuestaEnFuncionamiento;


    private ReceptorTemperatura receptorTemperatura;
    private ReceptorMovimiento receptorMovimiento;
    private boolean activa;
    private double ultimaTemperaturaRegistrada;
    private double tempMinima;
    private double tempMaxima;
    private boolean hayMovimiento;

    private Accionador accionadorParaTemperatura;
    private Accionador accionadorParaMovimiento;

    private UUID id;
    private List<MensajeSolicitudApertura> solicitudes;


    private static String BROKER_URL;
    private MqttClient client_solicitudes;
    private MqttClient client_intentos;
    private static String topic_solicitudes = "heladeras/solicitudes_de_apertura";
    private static String topic_intentos = "heladeras/intentos_de_apertura";


    @SneakyThrows
    public static Heladera of(HeladeraDTO dto) {
        HeladeraBuilder builder = Heladera
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
                .hayMovimiento(dto.isHayMovimiento());

        MqttClient client1 = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client1.subscribe(topic_solicitudes);

        MqttClient client2 = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client2.connect();

        builder.client_solicitudes(client1);
        return builder.build();
    }

    public String nombrePunto(){
        return this.nombre.getNombreDePunto();
    }

    private final List<ObserverSuscripcion> colaboradores = new ArrayList<>();

    public void suscribir(ObserverSuscripcion colaborador) {
        colaboradores.add(colaborador);
    }

    public void desuscribir(ObserverSuscripcion colaborador) {
        colaboradores.remove(colaborador);
    }

    public void notificarColaboradores() {
        colaboradores.forEach(colaborador -> colaborador.verificarEvento(this));
    }

    public void modificarViandas(Integer cantidad) {
        this.setCapacidadActual(this.getCapacidadActual() - cantidad);
        this.notificarColaboradores();
    }

    public void quitarViandas(Integer cantidad) {
        this.setCapacidadActual(this.getCapacidadActual() + cantidad);
        this.notificarColaboradores();
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

    public void agregarSolicitudApertura(MensajeSolicitudApertura soliApertura) {
        solicitudes.add(soliApertura);
    }

    @SneakyThrows
    public void verificarAcceso(String id, LocalDateTime fecha) {
        Optional<MensajeSolicitudApertura> aviso = solicitudes.stream().filter(soli -> soli.getIdTarjeta().equals(id)).findFirst();
        IntentoAperturaResuelto intento;
        ObjectMapper mapper = new ObjectMapper();
        if (aviso.isPresent()) {
            MensajeSolicitudApertura aviso_posta = aviso.get();
            solicitudes.remove(aviso_posta);
            if (aviso_posta.getFecha().isBefore(fecha)) {
                intento = new IntentoAperturaResuelto(id, this.id, fecha, false);
                throw new AccesoDenegadoHeladeraException("La solicitud de ingreso ya venció");
            } else {
                intento = new IntentoAperturaResuelto(id, this.id, fecha, true);
            }
        } else{
            intento = new IntentoAperturaResuelto(id, this.id, fecha, false);
            throw new AccesoDenegadoHeladeraException("No se encontró la solicitud de ingreso");
        }
        String jsonMensaje = mapper.writeValueAsString(intento);

        MqttMessage message = new MqttMessage(jsonMensaje.getBytes());
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
        MensajeSolicitudApertura msg = mapper.readValue(jsonMensaje, MensajeSolicitudApertura.class);

        if (msg.getIdHeladera().equals(this.id)) {
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
}

