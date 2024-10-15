package ar.edu.utn.frba.dds.models.repositories.suscripciones;

import ar.edu.utn.frba.dds.models.entities.suscripciones.SuscripcionAHeladera;
import ar.edu.utn.frba.dds.models.repositories.suscripciones.dao.SuscripcionDAO;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
@AllArgsConstructor
public class SuscripcionesRepository {
    private SuscripcionDAO suscripcion;

    public void guardar(SuscripcionAHeladera suscripcionAHeladera){suscripcion.guardar(suscripcionAHeladera);}

    public List<SuscripcionAHeladera> buscarTodos(){return suscripcion.buscarTodos();}

    public Optional<SuscripcionAHeladera> buscarPorId(Long id){return suscripcion.buscarPorId(id);}

    public Optional<SuscripcionAHeladera> buscarPorUsuarioIdYHeladeraId(Long usuarioId, Long heladeraId){return suscripcion.buscarPorUsuarioIdYHeladeraId(usuarioId,heladeraId);}

    public void eliminar(SuscripcionAHeladera suscripcionAHeladera){suscripcion.eliminar(suscripcionAHeladera);}

    void modificar(SuscripcionAHeladera suscripcionAHeladera){suscripcion.modificar(suscripcionAHeladera);}
}
