package ar.edu.utn.frba.dds.services.receptores;

import ar.edu.utn.frba.dds.controllers.HeladerasController;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.DonacionDeVianda;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.SolicitudApertura;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.IntentoAperturaResuelto;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.MotivoApertura;
import ar.edu.utn.frba.dds.models.entities.helpers.conversor_json.ConversorJSON;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.repositories.distribuciones_de_viandas.DistribucionesDeViandasRepository;
import ar.edu.utn.frba.dds.models.repositories.donaciones_de_vianda.DonacionesDeViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import ar.edu.utn.frba.dds.models.repositories.intentos_de_apertura.IntentosDeAperturaRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@NoArgsConstructor
@Setter
public class MqttReceptorIntento implements IMqttMessageListener {
    private final String BROKER_URL = "ssl://8e252e51d75f43e39ab207604b518d35.s1.eu.hivemq.cloud:8883";
    private static String topic_intentos = "heladeras/intentos_de_apertura";
    private MqttClient client_intentos;

    private HeladerasRepository heladeras;

    @SneakyThrows
    public MqttReceptorIntento(HeladerasRepository heladeras) {
        this.heladeras = heladeras;

        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName("ddslanaranjamecanica");
        options.setPassword("U2yZtv,^T2xWxapQw}r>".toCharArray());

        client_intentos = new MqttClient(BROKER_URL, "Receptor Intento", new MemoryPersistence());
        client_intentos.connect(options);
        client_intentos.subscribe(topic_intentos, this);

        System.out.println("Receptor de intentos de apertura de heladeras iniciado");
    }


    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

        JsonNode json = ConversorJSON.convertir(mqttMessage.toString());
        Long idHeladera = json.get("id_heladera").asLong();
        Long idTarjeta = json.get("id_tarjeta").asLong();
        Long idColab = json.get("id_colaboracion").asLong();
        Long idColaborador = json.get("id_colaborador").asLong();
        LocalDateTime fecha = LocalDateTime.parse(json.get("fecha").asText());
        Boolean exitoso = Objects.equals(json.get("acceso").asText(), "permitido");

        Optional<Heladera> posibleHeladera = heladeras.buscarPorId(idHeladera);


        if (posibleHeladera.isPresent()) {
            Heladera heladera = posibleHeladera.get();
            System.out.println("Heladera encontrada: " + posibleHeladera.get().getId());


            SolicitudApertura solicitudApertura = heladera.buscarSolicitud(idTarjeta).get();

            ColaboradorHumano colaborador = ServiceLocator.instanceOf(HumanosRepository.class).buscarPorIdUsuario(idColaborador).get();

            if (exitoso) {

                switch (solicitudApertura.getMotivoApertura()) {
                    case DONAR -> {

                        DonacionDeVianda donacion = ServiceLocator.instanceOf(DonacionesDeViandaRepository.class).buscarPorId(idColab).get();
                        donacion.setFinalizada(true);
                        ServiceLocator.instanceOf(DonacionesDeViandaRepository.class).actualizar(donacion);
                        heladera.agregarViandas(1);
                        colaborador.sumarPuntaje(donacion);

                    }
                    case COLOCAR -> {
                        DistribucionViandas distribucionViandas = ServiceLocator.instanceOf(DistribucionesDeViandasRepository.class).buscarPorId(idColab).get();
                        distribucionViandas.setColocadas(true);
                        ServiceLocator.instanceOf(DistribucionesDeViandasRepository.class).actualizar(distribucionViandas);
                        heladera.agregarViandas(distribucionViandas.getCantidadViandas());
                        colaborador.sumarPuntaje(distribucionViandas);
                    }
                    case RETIRAR -> {
                        DistribucionViandas distribucionViandas = ServiceLocator.instanceOf(DistribucionesDeViandasRepository.class).buscarPorId(idColab).get();
                        distribucionViandas.setRetiradas(true);
                        ServiceLocator.instanceOf(DistribucionesDeViandasRepository.class).actualizar(distribucionViandas);
                        heladera.quitarViandas(distribucionViandas.getCantidadViandas());
                        colaborador.sumarPuntaje(distribucionViandas);
                    }
                }
            }

            IntentoAperturaResuelto intento = new IntentoAperturaResuelto(colaborador.buscarTarjetaPorId(idTarjeta), colaborador, heladera, fecha, exitoso);
            ServiceLocator.instanceOf(IntentosDeAperturaRepository.class).guardar(intento);
        }


    }

    public Optional<Heladera> buscarHeladeraDestino(Long id) {
        return this.heladeras.buscarPorId(id);
    }
}
