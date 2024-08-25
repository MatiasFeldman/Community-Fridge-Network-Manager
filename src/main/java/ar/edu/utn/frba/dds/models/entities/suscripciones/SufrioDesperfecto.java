package ar.edu.utn.frba.dds.models.entities.suscripciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.Mensaje;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import lombok.Getter;

@Getter
public class SufrioDesperfecto implements MotivoNotificacion {
    public String cuerpo = "La heladera sufrió un desperfecto";
    public  String destinatario;
    private final IncidentesRepository incidentesRepository;

    public SufrioDesperfecto(IncidentesRepository incidentesRepository){
        this.incidentesRepository = incidentesRepository;
    }
    @Override
    public boolean validar(Heladera heladera){
        return incidentesRepository.buscarFallaTecnicaEnHeladera(heladera);
    }

    // falta implementar el metodo de getMensaje
    @Override
    public Mensaje getMensaje() {
        return new Mensaje(destinatario, cuerpo);
    }
}