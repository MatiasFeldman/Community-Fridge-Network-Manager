package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.sensores_y_receptores;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.SolicitudApertura;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.IntentoApertura;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.IntentoAperturaResuelto;
import ar.edu.utn.frba.dds.models.entities.helpers.conversor_json.ConversorJSON;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class ReceptorApertura implements IMqttMessageListener {
    private HeladerasRepository heladeras;
    private final String BROKER_URL = "ssl://8e252e51d75f43e39ab207604b518d35.s1.eu.hivemq.cloud:8883";
    private MqttClient cliente_solicitudes;
    private MqttClient cliente_intentos;

    @SneakyThrows
    public ReceptorApertura(HeladerasRepository heladeras) {
        this.heladeras = heladeras;

        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName("ddslanaranjamecanica");
        options.setPassword("U2yZtv,^T2xWxapQw}r>".toCharArray());

        cliente_solicitudes = new MqttClient(BROKER_URL, "ReceptorSolicitudes", new MemoryPersistence());
        cliente_solicitudes.connect(options);
        cliente_solicitudes.subscribe("heladeras/apertura", this);

        cliente_intentos = new MqttClient(BROKER_URL, "ReceptorIntentosApertura", new MemoryPersistence());
        cliente_intentos.connect(options);

        System.out.println("Receptor de apertura de heladeras iniciado");
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        JsonNode json = ConversorJSON.convertir(mqttMessage.toString());
        Long idTarjeta = json.get("id_tarjeta").asLong();
        Long idHeladera = json.get("id_heladera").asLong();

        System.out.println("Mensaje de apertura recibido: " + mqttMessage.toString());

        ColaboradorHumano colaborador = ServiceLocator.instanceOf(HumanosRepository.class).buscarPorTarjeta(idTarjeta).get();

        System.out.println("Colaborador: " + colaborador.getUsername());

        Optional<Heladera> posibleHeladera = heladeras.buscarPorId(idHeladera);

        System.out.println("Heladera: " + posibleHeladera.isPresent());

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonRta = mapper.createObjectNode();

        if (posibleHeladera.isPresent()) {
            Heladera heladera = posibleHeladera.get();
            SolicitudApertura solicitud = heladera.buscarSolicitud(idTarjeta).get();
            jsonRta.put("id_heladera", idHeladera);
            jsonRta.put("id_tarjeta", idTarjeta);
            jsonRta.put("id_colaborador", colaborador.getIdUsuario());
            jsonRta.put("fecha", LocalDateTime.now().toString());
            jsonRta.put("id_colaboracion", solicitud.getIdColaboracion());

            if (heladera.tieneAcceso(idTarjeta) && LocalDateTime.now().isBefore(solicitud.getFechaDeExpiracion())){
                jsonRta.put("acceso", "permitido");
            } else{
                jsonRta.put("acceso", "denegado");
            }

        } else{
            jsonRta.put("error", "Heladera no encontrada");
        }

        String rtaString = mapper.writeValueAsString(jsonRta);
        MqttMessage rta = new MqttMessage(rtaString.getBytes());
        cliente_intentos.publish("heladeras/intentos_de_apertura", rta);
    }
}
