package ar.edu.utn.frba.dds.models.repositories.tecnicos.dao;

import ar.edu.utn.frba.dds.models.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.NoResultException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class TecnicosDataBase implements TecnicosDAO, WithSimplePersistenceUnit {

    public void guardar(Tecnico tecnico) {
        tecnico.setPresente(true);
        beginTransaction();
        entityManager().persist(tecnico);
        commitTransaction();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tecnico> buscarTodos() {
        List<Tecnico> tecnicos = entityManager()
                .createQuery("from Tecnico where presente = true", Tecnico.class)
                .getResultList();

        tecnicos.forEach(t -> entityManager().refresh(t)); // Forzar sincronización de todas las entidades
        return tecnicos;
    }

    @Override
    public void eliminar(Tecnico tecnico) {
        tecnico.setPresente(false);
        this.modificar(tecnico);
    }

    @Override
    public Optional<Tecnico> buscarPorId(Long id) {
        Tecnico tecnico = entityManager().find(Tecnico.class, id);
        if (tecnico != null) {
            entityManager().refresh(tecnico); // Forzar sincronización de la entidad
        }
        return Optional.ofNullable(tecnico);
    }

    @Override
    public Optional<Tecnico> buscarPorIdUsuario(Long id) {
        try {
            Tecnico tecnico = entityManager()
                    .createQuery("SELECT h FROM Tecnico h WHERE h.user.id = :idUsuario AND h.presente = true", Tecnico.class)
                    .setParameter("idUsuario", id)
                    .getSingleResult();

            entityManager().refresh(tecnico); // Forzar sincronización de la entidad
            return Optional.ofNullable(tecnico);
        } catch (NoResultException e) {
            return Optional.empty(); // Si no encuentra resultados
        }
    }

    @Override
    public void modificar(Tecnico tecnico) {
        withTransaction(() -> {
            entityManager().merge(tecnico);
        });
    }

    @Override
    public Optional<Tecnico> buscarMasCercano(Direccion origen) {
        List<Tecnico> tecnicos = this.buscarTodos(); // buscarTodos ya sincroniza las entidades

        Optional<Tecnico> tecnicoConMismaDirec = tecnicos
                .stream()
                .filter(tecnico -> tecnico.getAreaCobertura().getDireccionRaiz().equals(origen)).findFirst();
        if (tecnicoConMismaDirec.isPresent()) {
            return tecnicoConMismaDirec;
        } else {
            return tecnicos
                    .stream()
                    .min(Comparator.comparing(t -> t.distanciaA(origen)));
        }
    }
}

