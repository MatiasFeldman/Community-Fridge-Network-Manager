package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dtos.incidentes.DenunciaFallaTecnicaDTO;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.*;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.IntentoApertura;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.IntentoAperturaResuelto;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.MensajeSolicitudApertura;
import ar.edu.utn.frba.dds.models.repositories.intentos_de_apertura.IntentosDeAperturaRepository;
import ar.edu.utn.frba.dds.models.repositories.solicitudes_de_apertura_de_heladera.SolicitudesDeAperturaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

@AllArgsConstructor
public class HeladerasController{
    private Accionador accionador;
    private SolicitudesDeAperturaRepository solicitudes;
    private IntentosDeAperturaRepository intentos;
    private MqttReceptorHeladera receptorHeladera;


    public void reportarFallaTecnica(Object solicitud){
        DenunciaFallaTecnicaDTO dto = (DenunciaFallaTecnicaDTO) solicitud;
        DenunciaFallaTecnica denuncia = DenunciaFallaTecnica.of(dto);

        accionador.registrarFallaTecnica(denuncia);
    }

    @SneakyThrows
    public void avisarApertura(Object solicitud){
        SolicitudApertura solicitudApertura = (SolicitudApertura) solicitud;

        MensajeSolicitudApertura msg = new MensajeSolicitudApertura(solicitudApertura.getIdHeladera(), solicitudApertura.getIdSolicitante() ,solicitudApertura.getFechaDeExpiracion());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(msg);

        receptorHeladera.publicarSolicitudApertura(jsonMessage);

        solicitudes.guardar(solicitudApertura);
    }

    //TODO: VER COMO GUARDAR LOS INTENTOS DE APERTURA YA RESUELTOS

}
