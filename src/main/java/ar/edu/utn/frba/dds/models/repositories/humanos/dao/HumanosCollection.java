package ar.edu.utn.frba.dds.models.repositories.humanos.dao;

import ar.edu.utn.frba.dds.models.entities.personas.Humano;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class HumanosCollection implements HumanosDAO, WithSimplePersistenceUnit {


    private List<Humano> humanos;
    public HumanosCollection(List<Humano> humanos) {
        this.humanos = humanos;
    } // ESTO SE DEBE ELIMINAR, LO DEJE PARA DISCUTIRLO DESPUES

    @Override
    public void guardar(Humano humano) {
        entityManager().persist(humano);    //INSERT
    }

    public void modificar(Humano humano) {
        withTransaction(() -> {
            entityManager().merge(humano);  //UPDATE
        });
    }

    @Override
    public void eliminar(Humano humano) {
        entityManager().remove(humano);     //DELETE
    } // ver si conviene eliminarlo o setearlo en inactivo

    @Override
    @SuppressWarnings("unchecked")
    public List<Humano> buscarTodos() {
        return entityManager()
                .createQuery("from " + Humano.class.getName())
                .getResultList();
    }
    @Override
    public Optional<Humano> buscarPorId(Long id){
        return Optional.ofNullable(entityManager().find(Humano.class, id));
    }

    @Override
    public boolean existeUsername(String username) {
        Long count = entityManager()
                .createQuery("SELECT COUNT(h) FROM Humano h WHERE h.username = :username", Long.class)
                .setParameter("username", username)
                .getSingleResult();

        return count > 0;
    }
}
