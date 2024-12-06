package ar.edu.utn.frba.dds.models.repositories.ofertas.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.Oferta;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class OfertasDataBase implements OfertasDAO, WithSimplePersistenceUnit {

    @Override
    public void guardar(Oferta oferta) {
        oferta.setPresente(true);
        beginTransaction();
        entityManager().persist(oferta);
        commitTransaction();
    }

    @Override
    public Optional<Oferta> buscarPorNombre(String nombre) {
        Optional<Oferta> oferta = entityManager()
                .createQuery("select o from Oferta o where o.nombre = :nombre and o.presente = true", Oferta.class)
                .setParameter("nombre", nombre)
                .getResultList()
                .stream()
                .findFirst();

        oferta.ifPresent(o -> entityManager().refresh(o)); // Forzar sincronización si la oferta existe
        return oferta;
    }

    @Override
    public void modficar(Oferta oferta) {
        withTransaction(() -> {
            entityManager().merge(oferta);
        });
    }

    @Override
    public List<Oferta> buscarPorRubro(String rubro) {
        List<Oferta> ofertas = entityManager()
                .createQuery("select o from Oferta o where o.rubro.nombre = :rubro and o.presente = true", Oferta.class)
                .setParameter("rubro", rubro)
                .getResultList();

        ofertas.forEach(o -> entityManager().refresh(o)); // Forzar sincronización de todas las ofertas
        return ofertas;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Oferta> buscarTodos() {
        List<Oferta> ofertas = entityManager()
                .createQuery("select o from Oferta o where o.presente = true ", Oferta.class)
                .getResultList();

        ofertas.forEach(o -> entityManager().refresh(o)); // Forzar sincronización de todas las ofertas
        return ofertas;
    }

    @Override
    public void eliminar(Oferta oferta) {
        oferta.setPresente(false);
        this.modficar(oferta);
    }

    @Override
    public Optional<Oferta> buscarPorId(Long id) {
        Oferta oferta = entityManager().find(Oferta.class, id);
        if (oferta != null) {
            entityManager().refresh(oferta); // Forzar sincronización con la base de datos
        }
        return Optional.ofNullable(oferta);
    }

    @Override
    public void canjearOferta(Oferta oferta) {
        oferta.serCanjeada();
        if (oferta.canjesRestantes() == 0) {
            this.eliminar(oferta);
        }
        this.modficar(oferta);
    }
}

