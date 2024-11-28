package ar.edu.utn.frba.dds.models.repositories.atributos_humano.dao;

import ar.edu.utn.frba.dds.models.entities.personas.Atributo;
import ar.edu.utn.frba.dds.models.entities.personas.TipoAtributo;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

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
        return entityManager()
                .createQuery("SELECT d FROM Atributo d WHERE d.presente = true", Atributo.class)
                .getResultList();
    }

    @Override
    public Optional<Atributo> buscarPorId(Long id) {
        return entityManager()
                .find(Atributo.class, id) == null ? Optional.empty() : Optional.of(entityManager().find(Atributo.class, id));
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
        return entityManager()
                .createQuery("SELECT d FROM Atributo d WHERE d.tipo = :tipo AND d.presente = true", Atributo.class)
                .setParameter("tipo", tipo)
                .getResultList();
    }

    @Override
    public Optional<Atributo> buscarPorNombre(String nombre) {
        return Optional.of(entityManager()
                .createQuery("SELECT d FROM Atributo d WHERE d.nombre = :nombre AND d.presente = true", Atributo.class)
                .setParameter("nombre", nombre)
                .getSingleResult());

    }

}
