package ar.edu.utn.frba.dds.models.repositories.donaciones_de_vianda.dao;

import ar.edu.utn.frba.dds.models.entities.colaboraciones.DonacionDeVianda;
import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class DonacionesDeViandaDataBase implements DonacionesDeViandaDAO, WithSimplePersistenceUnit {
    @Override
    public void guardar(DonacionDeVianda donacionDeVianda) {
        donacionDeVianda.setPresente(true);
        beginTransaction();
        entityManager().persist(donacionDeVianda);
        commitTransaction();
    }

    @Override
    public List<DonacionDeVianda> buscarTodas() {
        return entityManager()
                .createQuery("SELECT d FROM DonacionDeVianda d WHERE d.presente = true", DonacionDeVianda.class)
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
        donacionDeVianda.setPresente(false);
        this.actualizar(donacionDeVianda);

    }

    @Override
    public List<DonacionDeVianda> buscarPorColaborador(Long id) {
        return entityManager()
                .createQuery("SELECT d FROM DonacionDeVianda d WHERE d.colaborador.id = :id AND d.presente = true", DonacionDeVianda.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public Integer cantViandasDonadasPor(ColaboradorHumano colaborador) {
        LocalDate haceUnaSemana = LocalDate.now().minusWeeks(1);
        return entityManager()
                .createQuery("SELECT d FROM DonacionDeVianda d WHERE d.colaborador.user.id = :id AND d.presente = true AND d.fecha >= :haceUnaSemana", DonacionDeVianda.class)
                .setParameter("id", colaborador.getIdUsuario())
                .setParameter("haceUnaSemana", haceUnaSemana)
                .getResultList()
                .size();
    }
}
