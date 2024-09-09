package ar.edu.utn.frba.dds.models.repositories.incidentes.dao;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Incidente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class IncidentesDataBase implements WithSimplePersistenceUnit, IncidentesDAO {
    @Override
    public void guardar(Incidente incidente) {
        entityManager().persist(incidente);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Incidente> buscarTodos() {
        return entityManager()
                .createQuery("from " + Incidente.class.getName())
                .getResultList();
    }

    @Override
    public void eliminar(Incidente incidente) {
        entityManager().remove(incidente);
    }

    @Override
    public void modificar(Incidente incidente) {
        withTransaction(() -> {
            entityManager().merge(incidente);  //UPDATE
        });
    }

    @Override
    public Optional<Incidente> buscarIncidente(Long id) {
        return Optional.ofNullable(entityManager().find(Incidente.class, id));
    }

    @Override
    public boolean buscarFallaTecnicaEnHeladera(Heladera heladera) {
        Long count = (Long) entityManager()
                .createQuery("SELECT COUNT(i) FROM Incidente i WHERE i.heladera = :heladera AND i.tipo = 'FALLA_TECNICA'")
                .setParameter("heladera", heladera)
                .getSingleResult();

        return count > 0;
    }
}
