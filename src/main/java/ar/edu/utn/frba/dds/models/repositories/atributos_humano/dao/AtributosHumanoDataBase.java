package ar.edu.utn.frba.dds.models.repositories.atributos_humano.dao;

import ar.edu.utn.frba.dds.models.entities.personas.Atributo;
import ar.edu.utn.frba.dds.models.entities.personas.TipoAtributo;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public class AtributosHumanoDataBase implements WithSimplePersistenceUnit, AtributosHumanoDAO {

    @Override
    public void guardar(Atributo a) {
        a.setPresente(true);
        beginTransaction();
        entityManager().persist(a);
        commitTransaction();
    }

    @Override
    public List<Atributo> buscarTodas() {
        List<Atributo> atributos = entityManager()
                .createQuery("SELECT d FROM Atributo d WHERE d.presente = true", Atributo.class)
                .getResultList();

        atributos.forEach(attr -> entityManager().refresh(attr)); // Forzar sincronización de todas las entidades
        return atributos;
    }

    @Override
    public Optional<Atributo> buscarPorId(Long id) {
        Atributo atributo = entityManager().find(Atributo.class, id);
        if (atributo != null) {
            entityManager().refresh(atributo); // Forzar sincronización de la entidad
        }
        return Optional.ofNullable(atributo);
    }

    @Override
    public void actualizar(Atributo a) {
        beginTransaction();
        entityManager().merge(a);
        commitTransaction();
    }

    @Override
    public void eliminar(Atributo a) {
        a.setPresente(false);
        this.actualizar(a);
    }

    @Override
    public List<Atributo> buscarPorTipo(TipoAtributo tipo) {
        List<Atributo> atributos = entityManager()
                .createQuery("SELECT d FROM Atributo d WHERE d.tipo = :tipo AND d.presente = true", Atributo.class)
                .setParameter("tipo", tipo)
                .getResultList();

        atributos.forEach(attr -> entityManager().refresh(attr)); // Forzar sincronización de todas las entidades
        return atributos;
    }

    @Override
    public Optional<Atributo> buscarPorNombre(String nombre) {
        try {
            Atributo atributo = entityManager()
                    .createQuery("SELECT d FROM Atributo d WHERE d.nombre = :nombre AND d.presente = true", Atributo.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();

            entityManager().refresh(atributo); // Forzar sincronización de la entidad encontrada
            return Optional.ofNullable(atributo);
        } catch (NoResultException e) {
            return Optional.empty(); // Si no encuentra resultados
        }
    }
}

