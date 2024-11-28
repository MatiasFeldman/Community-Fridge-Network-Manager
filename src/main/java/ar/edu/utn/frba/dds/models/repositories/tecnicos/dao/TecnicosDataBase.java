package ar.edu.utn.frba.dds.models.repositories.tecnicos.dao;

import ar.edu.utn.frba.dds.models.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

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
        return entityManager()
                .createQuery("from Tecnico where presente = true", Tecnico.class)
                .getResultList();
    }

    @Override
    public void eliminar(Tecnico tecnico) {
        tecnico.setPresente(false);
        this.modificar(tecnico);
    }

    @Override
    public Optional<Tecnico> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager().find(Tecnico.class, id));
    }
    @Override
    public Optional<Tecnico> buscarPorIdUsuario(Long id) {
        return Optional.ofNullable(entityManager()
                .createQuery("SELECT h FROM Tecnico h WHERE h.user.id = :idUsuario AND h.presente = true", Tecnico.class)
                .setParameter("idUsuario", id)
                .getSingleResult());
    }

    @Override
    public void modificar(Tecnico tecnico) {
        withTransaction(() -> {
            entityManager().merge(tecnico);  //UPDATE
        });
    }

    @Override
    public Optional<Tecnico> buscarMasCercano(Direccion origen){
        List<Tecnico> tecnicos = this.buscarTodos();
        Optional<Tecnico> tecnicoConMismaDirec = tecnicos
                .stream()
                .filter(tecnico -> tecnico.getAreaCobertura().getDireccionRaiz().equals(origen)).findFirst();
        if (tecnicoConMismaDirec.isPresent()) return tecnicoConMismaDirec;
        else{
            return tecnicos
                    .stream()
                    .min(Comparator.comparing(t -> t.distanciaA(origen)));
        }
    }
}
