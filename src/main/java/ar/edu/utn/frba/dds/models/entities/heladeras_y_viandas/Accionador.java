package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import ar.edu.utn.frba.dds.dtos.incidentes.IncidenteDTO;
import ar.edu.utn.frba.dds.models.entities.comandos.Comando;
import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
public class Accionador {
    private Heladera heladera;
    private List<Comando> comandos;
    private IncidentesRepository incidentesRepository;

    public static Accionador of(Heladera heladera, IncidentesRepository incidentes) {
        return Accionador
                .builder()
                .heladera(heladera)
                .incidentesRepository(incidentes)
                .comandos(new ArrayList<>())
                .build();
    }

    public static Accionador of(Heladera heladera, List<Comando> comandos, IncidentesRepository incidentes) {
        return Accionador
                .builder()
                .heladera(heladera)
                .incidentesRepository(incidentes)
                .comandos(comandos)
                .build();
    }

    public void sucedeIncidente(TipoEvento tipo, LocalDateTime fecha) {
        this.registrarIncidente(tipo, fecha);
        this.comandos.forEach(Comando::ejecutar);
    }

    public void sucedeFallaTecnica(Humano denunciante, DenunciaFallaTecnica denuncia) {
        this.registrarFallaTecnica(denunciante, denuncia);
        this.comandos.forEach(Comando::ejecutar); // TODO: Avisar al tecnico correspondiente
    }

    public void registrarIncidente(TipoEvento tipo, LocalDateTime fecha) {
        IncidenteDTO dto = new IncidenteDTO(fecha, heladera, tipo);
        Incidente incidente = Incidente.of(dto);
        incidentesRepository.guardar(incidente);
        heladera.desactivar();
    }

    public void registrarFallaTecnica(Humano denunciante, DenunciaFallaTecnica denuncia) {
        IncidenteDTO dto = new IncidenteDTO(denuncia.getFecha(), heladera, TipoEvento.FALLA_TECNICA, denunciante, denuncia.getDescripcion(), denuncia.getFoto());
        Incidente incidente = Incidente.of(dto);
        incidentesRepository.guardar(incidente);
        heladera.desactivar();
    }
}
