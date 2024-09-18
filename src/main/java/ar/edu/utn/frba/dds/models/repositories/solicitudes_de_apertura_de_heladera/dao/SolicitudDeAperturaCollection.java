package ar.edu.utn.frba.dds.models.repositories.solicitudes_de_apertura_de_heladera.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaColaborador;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.SolicitudApertura;

import java.util.List;
import java.util.Optional;

public class SolicitudDeAperturaCollection implements SolicitudDeAperturaDAO{
    private List<SolicitudApertura> solicitudes;

    @Override
    public void guardar(SolicitudApertura solicitud){
        solicitudes.add(solicitud);
    }

    @Override
    public List<SolicitudApertura> buscarTodas(){
        return solicitudes;
    }

    @Override
    public void eliminar(SolicitudApertura solicitud){
        solicitudes.remove(solicitud);
    }

    @Override
    public void modificar(SolicitudApertura solicitud) {
        Optional<SolicitudApertura> posibleAEliminar = this.buscarSolicitudPorId(solicitud.getId());
        posibleAEliminar.ifPresent(solicitud1 -> {
            this.solicitudes.remove(solicitud1);
            this.solicitudes.add(solicitud);
        });
    }

    @Override
    public Boolean existeSolicitud(TarjetaColaborador tarjeta, Heladera heladera){
        return solicitudes.stream().anyMatch(solicitud -> solicitud.getIdTarjeta().equals(tarjeta.getId()) && solicitud.getIdHeladera().equals(heladera.getId()));
    }

    @Override
    public Optional<SolicitudApertura> buscarSolicitudPorId(Long id) {
        return solicitudes.stream().filter(solicitud -> solicitud.getId().equals(id)).findFirst();
    }

}
