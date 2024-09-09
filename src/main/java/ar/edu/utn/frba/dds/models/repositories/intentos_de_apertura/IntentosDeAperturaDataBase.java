package ar.edu.utn.frba.dds.models.repositories.intentos_de_apertura;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.apertura.IntentoAperturaResuelto;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class IntentosDeAperturaDataBase implements IntentosDeAperturaDAO, WithSimplePersistenceUnit {
    @Override
    public void guardar(IntentoAperturaResuelto intento) {
        entityManager().persist(intento);}

        @Override
    public List<IntentoAperturaResuelto> buscarTodos(){
            return entityManager()
                    .createQuery("from " + IntentoAperturaResuelto.class.getName())
                    .getResultList();
        }

    @Override
    public void eliminar(IntentoAperturaResuelto intento){
        entityManager().remove(intento);
    }
    @Override
    public void modficar(IntentoAperturaResuelto intento) {
        withTransaction(() -> {
            entityManager().merge(intento);
        });
    }
}
