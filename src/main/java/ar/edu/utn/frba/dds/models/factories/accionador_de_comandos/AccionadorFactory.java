package ar.edu.utn.frba.dds.models.factories.accionador_de_comandos;

import ar.edu.utn.frba.dds.models.entities.comandos.Alertar;
import ar.edu.utn.frba.dds.models.entities.comandos.AvisarTecnico;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Accionador;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import ar.edu.utn.frba.dds.models.repositories.tecnicos.TecnicosRepository;

import java.util.List;

public class AccionadorFactory {
    private IncidentesRepository incidentesRepository;
    private TecnicosRepository tecnicosDisponibles;

    public AccionadorFactory(IncidentesRepository incidentesRepository) {
        this.incidentesRepository = incidentesRepository;
    }

    public AccionadorFactory(IncidentesRepository incidentesRepository, TecnicosRepository tecnicosDiponibles) {
        this.incidentesRepository = incidentesRepository;
        this.tecnicosDisponibles = tecnicosDiponibles;
    }

    public Accionador crearParaFallaTecnica() {
        return Accionador.of(List.of(new AvisarTecnico(tecnicosDisponibles)), incidentesRepository);
    }

    public Accionador crearParaTemperatura() {
        return Accionador.of(List.of(new Alertar()), incidentesRepository);
    }

    public Accionador crearParaMovimiento() {
        return Accionador.of(List.of(new Alertar()), incidentesRepository);
    }

}
