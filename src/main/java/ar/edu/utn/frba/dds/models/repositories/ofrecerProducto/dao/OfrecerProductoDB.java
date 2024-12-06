package ar.edu.utn.frba.dds.models.repositories.ofrecerProducto.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.OfrecerProductoOServicio;
import ar.edu.utn.frba.dds.models.entities.personas.Juridica;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class OfrecerProductoDB implements OfrecerProductoDAO, WithSimplePersistenceUnit {

    @Override
    public void guardar(OfrecerProductoOServicio ofrecerProductoOServicio) {
        ofrecerProductoOServicio.setPresente(true);
        beginTransaction();
        entityManager().persist(ofrecerProductoOServicio);
        commitTransaction();
    }

    @Override
    public List<OfrecerProductoOServicio> buscarTodas() {
        List<OfrecerProductoOServicio> productos = entityManager()
                .createQuery("SELECT c FROM OfrecerProductoOServicio c WHERE c.presente = true", OfrecerProductoOServicio.class)
                .getResultList();

        productos.forEach(p -> entityManager().refresh(p)); // Forzar sincronización de todas las entidades
        return productos;
    }

    @Override
    public Optional<OfrecerProductoOServicio> buscarPorId(Long id) {
        OfrecerProductoOServicio producto = entityManager().find(OfrecerProductoOServicio.class, id);
        if (producto != null) {
            entityManager().refresh(producto); // Forzar sincronización si la entidad existe
        }
        return Optional.ofNullable(producto);
    }

    @Override
    public void actualizar(OfrecerProductoOServicio ofrecerProductoOServicio) {
        beginTransaction();
        entityManager().merge(ofrecerProductoOServicio);
        commitTransaction();
    }

    @Override
    public void eliminar(OfrecerProductoOServicio ofrecerProductoOServicio) {
        ofrecerProductoOServicio.setPresente(false);
        this.actualizar(ofrecerProductoOServicio);
    }

    @Override
    public Integer cantProductosOfrecidosPor(Juridica colaborador) {
        List<OfrecerProductoOServicio> productos = entityManager()
                .createQuery("SELECT d FROM OfrecerProductoOServicio d WHERE d.juridica.user.id = :id AND d.presente = true", OfrecerProductoOServicio.class)
                .setParameter("id", colaborador.getId())
                .getResultList();

        productos.forEach(p -> entityManager().refresh(p)); // Forzar sincronización de las entidades
        return productos.size();
    }
}

