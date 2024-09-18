package ar.edu.utn.frba.dds.models.repositories.solicitudes_de_apertura_de_heladera.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaColaborador;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.SolicitudApertura;

import java.util.List;
import java.util.Optional;

public interface SolicitudDeAperturaDAO {
    void guardar(SolicitudApertura solicitud);
    void eliminar(SolicitudApertura solicitud);
    void modificar(SolicitudApertura solicitud);
    List<SolicitudApertura> buscarTodas();
    Boolean existeSolicitud(TarjetaColaborador idTarjeta, Heladera idHeladera);
    Optional<SolicitudApertura> buscarSolicitudPorId(Long id);


}
