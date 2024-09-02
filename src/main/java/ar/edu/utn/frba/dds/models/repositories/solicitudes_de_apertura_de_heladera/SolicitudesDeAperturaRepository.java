package ar.edu.utn.frba.dds.models.repositories.solicitudes_de_apertura_de_heladera;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaHumano;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.SolicitudApertura;

import java.util.List;
import java.util.Optional;

public class SolicitudesDeAperturaRepository {
    private List<SolicitudApertura> solicitudes;

    public void guardar(SolicitudApertura solicitud){
        solicitudes.add(solicitud);
    }

    public List<SolicitudApertura> buscarTodas(){
        return solicitudes;
    }

    public void eliminar(SolicitudApertura solicitud){
        solicitudes.remove(solicitud);
    }

    public Boolean existeSolicitud(TarjetaHumano tarjeta, Heladera heladera){
        return solicitudes.stream().anyMatch(solicitud -> solicitud.getIdTarjeta().equals(tarjeta.getId()) && solicitud.getIdHeladera().equals(heladera.getId()));
    }

    public Optional<SolicitudApertura> buscarSolicitud(TarjetaHumano tarjeta, Heladera heladera){
        return solicitudes.stream().filter(solicitud -> solicitud.getIdTarjeta().equals(tarjeta.getId()) && solicitud.getIdHeladera().equals(heladera.getId())).findFirst();
    }
}
