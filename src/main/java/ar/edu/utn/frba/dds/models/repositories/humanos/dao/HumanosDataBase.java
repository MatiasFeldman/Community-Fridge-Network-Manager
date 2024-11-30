package ar.edu.utn.frba.dds.models.repositories.humanos.dao;

import ar.edu.utn.frba.dds.models.entities.personas.ColaboradorHumano;
import ar.edu.utn.frba.dds.models.entities.tecnicos.Tipo_documento;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public class HumanosDataBase implements HumanosDAO, WithSimplePersistenceUnit {

    @Override
    public void guardar(ColaboradorHumano colaboradorHumano) {
        colaboradorHumano.setPresente(true);
        beginTransaction();
        entityManager().persist(colaboradorHumano);
        commitTransaction();
    }

    public void modificar(ColaboradorHumano colaboradorHumano) {
        withTransaction(() -> {
            entityManager().merge(colaboradorHumano);
        });
    }

    @Override
    public void eliminar(ColaboradorHumano colaboradorHumano) {
        colaboradorHumano.setPresente(false);
        this.modificar(colaboradorHumano);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ColaboradorHumano> buscarTodos() {
        return entityManager()
                .createQuery("SELECT h FROM ColaboradorHumano h WHERE h.presente = true ", ColaboradorHumano.class)
                .getResultList();
    }
    @Override
    public Optional<ColaboradorHumano> buscarPorIdUsuario(Long id) {
        return Optional.ofNullable(entityManager()
                .createQuery("SELECT h FROM ColaboradorHumano h WHERE h.user.id = :idUsuario AND h.presente = true", ColaboradorHumano.class)
                .setParameter("idUsuario", id)
                .getSingleResult());
    }

    @Override
    public boolean existeUsername(String username) {
        Long count = entityManager()
                .createQuery("SELECT COUNT(h) FROM ColaboradorHumano h WHERE h.user.user = :username AND h.presente = true", Long.class)
                .setParameter("username", username)
                .getSingleResult();

        return count > 0;
    }

    @Override
    public Optional<ColaboradorHumano> buscarPorTarjeta(Long id) {
        return Optional.ofNullable(entityManager()
                .createQuery("SELECT h FROM ColaboradorHumano h JOIN h.tarjetas t WHERE t.id = :idTarjeta AND h.presente = true", ColaboradorHumano.class)
                .setParameter("idTarjeta", id)
                .getSingleResult());
    }


    @Override
    public Optional<ColaboradorHumano> buscarPorDocumento(String tipo, String nro) {
        System.out.println("tipo: " + tipo + " nro: " + nro);
        try {
            System.out.println("tipo: " + tipo + " nro: " + nro);
            return Optional.ofNullable(entityManager()
                    .createQuery("SELECT h FROM ColaboradorHumano h WHERE h.tipoDocumento = :tipoDoc AND h.documento = :nroDoc AND h.presente = true", ColaboradorHumano.class)
                    .setParameter("tipoDoc", Tipo_documento.valueOf(tipo))
                    .setParameter("nroDoc", nro)
                    .getSingleResult()); // Si no encuentra, lanzará NoResultException
        } catch (NoResultException e) {
            System.out.println("No se encontraron resultados para el documento");
            return Optional.empty(); // Devuelve Optional vacío si no hay resultados
        } catch (Exception e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            return Optional.empty(); // Devuelve Optional vacío si hay un error
        }
    }
}
