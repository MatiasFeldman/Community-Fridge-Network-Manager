package ar.edu.utn.frba.dds.models.repositories.heladeras;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Comuna;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Provincia;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class HeladerasDataBase implements HeladerasDAO, WithSimplePersistenceUnit {
    @Override
    public void guardar(Heladera heladera) {
        beginTransaction();
        entityManager().persist(heladera);
        commitTransaction();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Heladera> buscarTodos() {
        return entityManager()
                .createQuery("SELECT h FROM Heladera h WHERE h.presente = true ", Heladera.class)
                .getResultList();
    }

    @Override
    public void modificar(Heladera heladera) {
        withTransaction(() -> {
            entityManager().merge(heladera);
        });
    }

    @Override
    public List<Heladera> buscarHeladerasPorDireccion(String valorBusqueda) {
        return entityManager()
                .createQuery("SELECT h FROM Heladera h WHERE h.direccion.direccion = :direc AND h.presente = true ", Heladera.class)
                .setParameter("direc", valorBusqueda)
                .getResultList();
    }

    @Override
    public List<Heladera> buscarHeladerasPorComuna(String valorBusqueda) {
        Comuna comuna = new Comuna(valorBusqueda.toLowerCase());
        return entityManager()
                .createQuery("SELECT h FROM Heladera h WHERE h.direccion.comuna = :comuna AND h.presente = true ", Heladera.class)
                .setParameter("comuna", comuna)
                .getResultList();
    }


    @Override
    public void eliminar(Heladera heladera) {
        heladera.setPresente(false);
        this.modificar(heladera);
    }

    @Override
    public Optional<Heladera> buscarPorNombre(String name) {
        Heladera h = entityManager()
                .createQuery("SELECT h FROM Heladera h WHERE h.nombre = :name AND h.presente = true ", Heladera.class)
                .setParameter("name", name)
                .getSingleResult();

        return Optional.ofNullable(h);
    }

    @Override
    public Optional<Heladera> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(Heladera.class, id));
    }
}
