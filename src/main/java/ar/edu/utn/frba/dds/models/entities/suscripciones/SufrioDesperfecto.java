package ar.edu.utn.frba.dds.models.entities.suscripciones;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import lombok.Getter;

@Getter
public class SufrioDesperfecto implements MotivoNotificacion {
    public String mensaje = "La heladera sufrió un desperfecto";
    private final IncidentesRepository incidentesRepository;

    public SufrioDesperfecto(IncidentesRepository incidentesRepository){
        this.incidentesRepository = incidentesRepository;
    }

    public boolean validar(Heladera heladera){
        return incidentesRepository.buscarFallaTecnicaEnHeladera(heladera);
    }
}