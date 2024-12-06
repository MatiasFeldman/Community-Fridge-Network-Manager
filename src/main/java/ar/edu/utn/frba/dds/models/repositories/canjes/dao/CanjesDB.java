package ar.edu.utn.frba.dds.models.repositories.canjes.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Canjes;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.OfrecerProductoOServicio;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class CanjesDB implements CanjesDAO, WithSimplePersistenceUnit {

    @Override
    public void guardar(Canjes canje) {
        canje.setPresente(true);
        beginTransaction();
        entityManager().persist(canje);
        commitTransaction();
    }

    @Override
    public List<Canjes> buscarTodas() {
        List<Canjes> canjes = entityManager()
                .createQuery("SELECT c FROM Canjes c WHERE c.presente = true", Canjes.class)
                .getResultList();

        canjes.forEach(c -> entityManager().refresh(c)); // Forzar sincronización de todas las entidades
        return canjes;
    }

    @Override
    public Optional<Canjes> buscarPorId(Long id) {
        Canjes canje = entityManager().find(Canjes.class, id);
        if (canje != null) {
            entityManager().refresh(canje); // Forzar sincronización de la entidad
        }
        return Optional.ofNullable(canje);
    }

    @Override
    public void actualizar(Canjes canje) {
        beginTransaction();
        entityManager().merge(canje);
        commitTransaction();
    }

    @Override
    public void eliminar(Canjes canje) {
        canje.setPresente(false);
        this.actualizar(canje);
    }

    @Override
    public List<Canjes> buscarPorUsuario(Usuario usuario) {
        List<Canjes> canjes = entityManager()
                .createQuery("SELECT c FROM Canjes c WHERE c.usuario.id = :idUsuario AND c.presente = true", Canjes.class)
                .setParameter("idUsuario", usuario.getId())
                .getResultList();

        canjes.forEach(c -> entityManager().refresh(c)); // Forzar sincronización de todas las entidades
        return canjes;
    }
}

