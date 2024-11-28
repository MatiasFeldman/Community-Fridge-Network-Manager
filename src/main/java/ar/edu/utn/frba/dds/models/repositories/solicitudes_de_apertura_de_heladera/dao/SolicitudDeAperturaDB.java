package ar.edu.utn.frba.dds.models.repositories.solicitudes_de_apertura_de_heladera.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.TarjetaColaborador;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.SolicitudApertura;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class SolicitudDeAperturaDB implements WithSimplePersistenceUnit, SolicitudDeAperturaDAO {
    @Override
    public void guardar(SolicitudApertura solicitud) {
        solicitud.setPresente(true);
        beginTransaction();
        entityManager().persist(solicitud);
        commitTransaction();
    }

    @Override
    public void eliminar(SolicitudApertura solicitud) {
        solicitud.setPresente(false);
        this.modificar(solicitud);
    }

    @Override
    public void modificar(SolicitudApertura solicitud) {
        beginTransaction();
        entityManager().merge(solicitud);
        commitTransaction();
    }

    @Override
    public List<SolicitudApertura> buscarTodas() {
        return entityManager()
                .createQuery("from SolicitudApertura where presente = true", SolicitudApertura.class)
                .getResultList();
    }

    @Override
    public Boolean existeSolicitud(TarjetaColaborador idTarjeta, Heladera idHeladera) {
        return !entityManager()
                .createQuery("from SolicitudApertura where solicitante = :idTarjeta and heladera = :idHeladera and presente = true", SolicitudApertura.class)
                .setParameter("idTarjeta", idTarjeta)
                .setParameter("idHeladera", idHeladera)
                .getResultList().isEmpty();
    }

    @Override
    public Optional<SolicitudApertura> buscarSolicitudPorId(Long id) {
        return entityManager().find(SolicitudApertura.class, id) == null ? Optional.empty() : Optional.of(entityManager().find(SolicitudApertura.class, id));
    }

}
