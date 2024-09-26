package ar.edu.utn.frba.dds.models.repositories.solicitudes_de_apertura_de_heladera;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaColaborador;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.SolicitudApertura;
import ar.edu.utn.frba.dds.models.repositories.solicitudes_de_apertura_de_heladera.dao.SolicitudDeAperturaDAO;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class SolicitudesDeAperturaRepository {
    private SolicitudDeAperturaDAO dao;

    public void guardar(SolicitudApertura solicitud){
        dao.guardar(solicitud);
    }

    public List<SolicitudApertura> buscarTodas(){
        return dao.buscarTodas();
    }

    public void eliminar(SolicitudApertura solicitud){
        dao.eliminar(solicitud);
    }

    public Boolean existeSolicitud(TarjetaColaborador tarjeta, Heladera heladera){
        return dao.existeSolicitud(tarjeta, heladera);
    }

    public void modificar(SolicitudApertura solicitud){dao.modificar(solicitud);}

    Optional<SolicitudApertura> buscarPorId(Long id){
        return dao.buscarSolicitudPorId(id);
    }
}
