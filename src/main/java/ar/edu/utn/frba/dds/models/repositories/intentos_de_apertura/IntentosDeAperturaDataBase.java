package ar.edu.utn.frba.dds.models.repositories.intentos_de_apertura;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.IntentoAperturaResuelto;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class IntentosDeAperturaDataBase implements IntentosDeAperturaDAO, WithSimplePersistenceUnit {

    @Override
    public void guardar(IntentoAperturaResuelto intento) {
        intento.setPresente(true);
        beginTransaction();
        entityManager().persist(intento);
        commitTransaction();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<IntentoAperturaResuelto> buscarTodos() {
        List<IntentoAperturaResuelto> intentos = entityManager()
                .createQuery("SELECT i FROM IntentoAperturaResuelto i WHERE i.presente = true ", IntentoAperturaResuelto.class)
                .getResultList();

        intentos.forEach(i -> entityManager().refresh(i)); // Forzar sincronización de todas las entidades
        return intentos;
    }

    @Override
    public void eliminar(IntentoAperturaResuelto intento) {
        intento.setPresente(false);
        this.modficar(intento);
    }

    @Override
    public void modficar(IntentoAperturaResuelto intento) {
        withTransaction(() -> {
            entityManager().merge(intento);
        });
    }
}
