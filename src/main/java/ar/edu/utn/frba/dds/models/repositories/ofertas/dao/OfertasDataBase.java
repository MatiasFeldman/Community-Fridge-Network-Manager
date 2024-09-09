package ar.edu.utn.frba.dds.models.repositories.ofertas.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Oferta;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class OfertasDataBase implements OfertasDAO, WithSimplePersistenceUnit {

    @Override
    public void guardar(Oferta oferta) {
        beginTransaction();
        entityManager().persist(oferta);
        commitTransaction();
    }

    @Override
    public Optional<Oferta> buscarPorNombre(String nombre) {
        return entityManager()
                .createQuery("select o from Oferta o where o.nombre = :nombre", Oferta.class)
                .setParameter("nombre", nombre)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public void modficar(Oferta oferta) {
        withTransaction(() -> {
            entityManager().merge(oferta);
        });
    }

    @Override
    public List<Oferta> buscarPorRubro(String rubro) {
        return entityManager()
                .createQuery("select o from Oferta o where o.rubro.nombre = :rubro", Oferta.class)
                .setParameter("rubro", rubro)
                .getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Oferta> buscarTodos() {
        return entityManager()
                .createQuery("from " + Oferta.class.getName())
                .getResultList();
    }

    @Override
    public void eliminar(Oferta oferta) {
        beginTransaction();
        entityManager().remove(oferta);
        commitTransaction();
    }

    @Override
    public Optional<Oferta> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(Oferta.class, id));
    }
}
