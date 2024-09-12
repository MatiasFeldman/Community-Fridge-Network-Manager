package ar.edu.utn.frba.dds.models.repositories.donaciones_de_vianda.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.DonacionDeVianda;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class DonacionesDeViandaDataBase implements DonacionesDeViandaDAO, WithSimplePersistenceUnit {
    @Override
    public void guardar(DonacionDeVianda donacionDeVianda) {
        beginTransaction();
        entityManager().persist(donacionDeVianda);
        commitTransaction();
    }

    @Override
    public List<DonacionDeVianda> buscarTodas() {
        return entityManager()
                .createQuery("SELECT d FROM DonacionDeVianda d WHERE d.activa = true", DonacionDeVianda.class)
                .getResultList();
    }

    @Override
    public Optional<DonacionDeVianda> buscarPorId(Long id) {
        return entityManager()
                .find(DonacionDeVianda.class, id) == null ? Optional.empty() : Optional.of(entityManager().find(DonacionDeVianda.class, id));
    }

    @Override
    public void actualizar(DonacionDeVianda donacionDeVianda) {
        beginTransaction();
        entityManager().merge(donacionDeVianda);
        commitTransaction();
    }

    @Override
    public void eliminar(DonacionDeVianda donacionDeVianda) {
        donacionDeVianda.setActiva(false);
        this.actualizar(donacionDeVianda);

    }

    @Override
    public List<DonacionDeVianda> buscarPorColaborador(Long id) {
        return entityManager()
                .createQuery("SELECT d FROM DonacionDeVianda d WHERE d.colaborador.id = :id AND d.activa = true", DonacionDeVianda.class)
                .setParameter("id", id)
                .getResultList();
    }
}
