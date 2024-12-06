package ar.edu.utn.frba.dds.models.repositories.heladeras;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Comuna;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Provincia;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public class HeladerasDataBase implements HeladerasDAO, WithSimplePersistenceUnit {

    @Override
    public void guardar(Heladera heladera) {
        heladera.setPresente(true);
        beginTransaction();
        entityManager().persist(heladera);
        commitTransaction();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Heladera> buscarTodos() {
        List<Heladera> heladeras = entityManager()
                .createQuery("SELECT h FROM Heladera h WHERE h.presente = true ", Heladera.class)
                .getResultList();

        heladeras.forEach(h -> entityManager().refresh(h)); // Forzar sincronización de todas las entidades
        return heladeras;
    }

    @Override
    public void modificar(Heladera heladera) {
        withTransaction(() -> {
            entityManager().merge(heladera);
        });
    }

    @Override
    public List<Heladera> buscarHeladerasPorDireccion(String valorBusqueda) {
        List<Heladera> heladeras = entityManager()
                .createQuery("SELECT h FROM Heladera h WHERE h.direccion.direccion = :direc AND h.presente = true ", Heladera.class)
                .setParameter("direc", valorBusqueda)
                .getResultList();

        heladeras.forEach(h -> entityManager().refresh(h)); // Forzar sincronización de todas las entidades
        return heladeras;
    }

    @Override
    public List<Heladera> buscarHeladerasPorComuna(String valorBusqueda) {
        Comuna comuna = new Comuna(valorBusqueda.toLowerCase());
        List<Heladera> heladeras = entityManager()
                .createQuery("SELECT h FROM Heladera h WHERE h.direccion.comuna = :comuna AND h.presente = true ", Heladera.class)
                .setParameter("comuna", comuna)
                .getResultList();

        heladeras.forEach(h -> entityManager().refresh(h)); // Forzar sincronización de todas las entidades
        return heladeras;
    }

    @Override
    public void eliminar(Heladera heladera) {
        heladera.setPresente(false);
        this.modificar(heladera);
    }

    @Override
    public Optional<Heladera> buscarPorNombre(String name) {
        try {
            Heladera h = entityManager()
                    .createQuery("SELECT h FROM Heladera h WHERE h.nombre = :name AND h.presente = true ", Heladera.class)
                    .setParameter("name", name)
                    .getSingleResult();

            entityManager().refresh(h); // Forzar sincronización de la entidad encontrada
            return Optional.ofNullable(h);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Heladera> buscarPorId(Long id) {
        Heladera heladera = entityManager().find(Heladera.class, id);
        if (heladera != null) {
            entityManager().refresh(heladera); // Forzar sincronización de la entidad
        }
        return Optional.ofNullable(heladera);
    }
}
