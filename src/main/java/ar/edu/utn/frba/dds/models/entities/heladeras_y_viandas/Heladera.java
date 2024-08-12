package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;


import ar.edu.utn.frba.dds.dtos.heladeras.HeladeraDTO;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.ACCION_APERTURA;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.IntentoAperturaResuelto;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.MensajeSolicitudApertura;
import ar.edu.utn.frba.dds.models.entities.suscripciones.ObserverSuscripcion;
import ar.edu.utn.frba.dds.exceptions.AccesoDenegadoHeladeraException;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.models.repositories.intentos_de_apertura.IntentosDeAperturaRepository;
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
    private boolean activa;
    private double ultimaTemperaturaRegistrada;
    private double tempMinima;
    private double tempMaxima;
    private boolean hayMovimiento;
    private UUID id;
    private List<MensajeSolicitudApertura> solicitudes = new ArrayList<>();
    private IntentosDeAperturaRepository intentos;


    private static String BROKER_URL;
    private MqttClient client;
    private static String topic = "heladeras/solicitudes_de_apertura";

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
                .hayMovimiento(dto.isHayMovimiento())
                .intentos(dto.getIntentos());

        MqttClient client1 = new MqttClient(BROKER_URL, MqttClient.generateClientId());
        client1.subscribe(topic);
        builder.client(client1);
        return builder.build();
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

    public void verificarAcceso(String id, LocalDateTime fecha) {
        Optional<MensajeSolicitudApertura> aviso = solicitudes.stream().filter(soli -> soli.getIdTarjeta().equals(id)).findFirst();
        if (aviso.isPresent()){
            MensajeSolicitudApertura aviso_posta = aviso.get();
            solicitudes.remove(aviso_posta);
            if (aviso_posta.getFecha().isBefore(fecha)){
                IntentoAperturaResuelto intento = new IntentoAperturaResuelto(id, this.id, fecha, false);
                intentos.guardar(intento);
                throw new AccesoDenegadoHeladeraException("La solicitud de ingreso ya venció");
            } else{
                IntentoAperturaResuelto intento = new IntentoAperturaResuelto(id, this.id, fecha, true);
                intentos.guardar(intento);
            }
        }

    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        String jsonMensaje = mqttMessage.toString();
        ObjectMapper mapper = new ObjectMapper();
        MensajeSolicitudApertura msg = mapper.readValue(jsonMensaje, MensajeSolicitudApertura.class);

        if (msg.getIdHeladera().equals(this.id)) {
            if (msg.getAccion().equals(ACCION_APERTURA.AVISO)) {
                this.agregarSolicitudApertura(msg);
            } else if (msg.getAccion().equals(ACCION_APERTURA.INTENTO)) {
                this.verificarAcceso(msg.getIdTarjeta(), msg.getFecha());
            }
        }
    }
}
