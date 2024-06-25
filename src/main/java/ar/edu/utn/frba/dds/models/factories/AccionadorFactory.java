package ar.edu.utn.frba.dds.models.factories;

import ar.edu.utn.frba.dds.models.entities.comandos.ActivarAlarma;
import ar.edu.utn.frba.dds.models.entities.comandos.Alertar;
import ar.edu.utn.frba.dds.models.entities.comandos.AvisarTecnico;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Accionador;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;

import java.util.List;

public class AccionadorFactory {
    private IncidentesRepository incidentesRepository;

    public AccionadorFactory(IncidentesRepository incidentesRepository) {
        this.incidentesRepository = incidentesRepository;
    }

    public Accionador crearParaFallaTecnica(Heladera heladera) {
        return Accionador.of(heladera, List.of(new AvisarTecnico()), incidentesRepository);
    }

    public Accionador crearParaTemperatura(Heladera heladera) {
        return Accionador.of(heladera, List.of(new Alertar()), incidentesRepository);
    }

    public Accionador crearParaMovimiento(Heladera heladera) {
        return Accionador.of(heladera, List.of(new ActivarAlarma()), incidentesRepository);
    }

}
