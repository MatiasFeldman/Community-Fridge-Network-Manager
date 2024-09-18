package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import ar.edu.utn.frba.dds.dtos.incidentes.IncidenteDTO;
import ar.edu.utn.frba.dds.models.entities.comandos.Comando;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
public class Accionador {
    private List<Comando> comandos;
    private IncidentesRepository incidentesRepository;

    public static Accionador of(IncidentesRepository incidentes) {
        return Accionador
                .builder()
                .incidentesRepository(incidentes)
                .comandos(new ArrayList<>())
                .build();
    }

    public static Accionador of(List<Comando> comandos, IncidentesRepository incidentes) {
        return Accionador
                .builder()
                .incidentesRepository(incidentes)
                .comandos(comandos)
                .build();
    }

    public void sucedeIncidente(TipoEvento tipo, LocalDateTime fecha, Heladera heladera) {
        this.registrarIncidente(tipo, fecha, heladera);
        this.comandos.forEach(comando -> comando.ejecutar(heladera, tipo.name()));
    }

    public void sucedeFallaTecnica(DenunciaFallaTecnica denuncia, Heladera heladera) {
        this.registrarFallaTecnica(denuncia);
        this.comandos.forEach(comando -> comando.ejecutar(heladera, denuncia.getDescripcion()));

    }

    public void registrarIncidente(TipoEvento tipo, LocalDateTime fecha, Heladera heladera) {
        IncidenteDTO dto = new IncidenteDTO(fecha, heladera, tipo);
        Incidente incidente = Incidente.of(dto);
        incidentesRepository.guardar(incidente);
        heladera.desactivar();
    }

    public void registrarFallaTecnica(DenunciaFallaTecnica denuncia) {
        Heladera heladera = denuncia.getHeladera();
        IncidenteDTO dto = new IncidenteDTO(denuncia.getFecha(),
                                            heladera,
                                            TipoEvento.FALLA_TECNICA,
                                            denuncia.getDenunciante(),
                                            denuncia.getDescripcion(), denuncia.getFoto());
        Incidente incidente = Incidente.of(dto);
        incidentesRepository.guardar(incidente);
        heladera.desactivar();
    }
}
