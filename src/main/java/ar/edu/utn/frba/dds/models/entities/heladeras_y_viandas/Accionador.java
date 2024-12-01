package ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas;

import ar.edu.utn.frba.dds.dtos.incidentes.IncidenteDTO;
import ar.edu.utn.frba.dds.models.entities.comandos.Comando;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
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

    public void agregarComando(Comando comando) {
        this.comandos.add(comando);
    }

    public void sucedeIncidente(TipoEvento tipo, LocalDateTime fecha, Heladera heladera) {
        System.out.println(heladera);
        this.registrarIncidente(tipo, fecha, heladera);
        System.out.println(this.comandos.size());
        this.comandos.forEach(c -> System.out.println("Ejecutando comando: " + c.getClass().getSimpleName()));
        this.comandos.forEach(comando -> comando.ejecutar(heladera, tipo.toString()));
        ServiceLocator.instanceOf(HeladerasRepository.class).modificar(heladera);
    }

    public void sucedeFallaTecnica(DenunciaFallaTecnica denuncia, Heladera heladera) {
        this.registrarFallaTecnica(denuncia);
        this.comandos.forEach(comando -> comando.ejecutar(heladera, denuncia.getDescripcion()));

    }

    public void registrarIncidente(TipoEvento tipo, LocalDateTime fecha, Heladera heladera) {

        heladera.desactivar();

        IncidenteDTO dto = new IncidenteDTO(fecha, heladera, tipo);

        Incidente incidente = Incidente.of(dto);

        System.out.println("Voy a guardar el incidente");



        ServiceLocator.instanceOf(IncidentesRepository.class).guardar(incidente);

        System.out.println("Se ha registrado un incidente de tipo " + incidente.getTipo() + " en la heladera: " + incidente.getHeladera().getId());

    }

    public void registrarFallaTecnica(DenunciaFallaTecnica denuncia) {
        Heladera heladera = denuncia.getHeladera();
        IncidenteDTO dto = IncidenteDTO.of(denuncia.getDenunciante(),
                denuncia.getFecha(),
                heladera,
                TipoEvento.FALLA_TECNICA,
                denuncia.getDescripcion(),
                denuncia.getFoto());
        Incidente incidente = Incidente.of(dto);
        ServiceLocator.instanceOf(IncidentesRepository.class).guardar(incidente);

        heladera.desactivar();

        ServiceLocator.instanceOf(HeladerasRepository.class).modificar(heladera);
    }
}
