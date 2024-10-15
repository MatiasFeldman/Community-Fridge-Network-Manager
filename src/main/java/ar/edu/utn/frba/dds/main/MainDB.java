package ar.edu.utn.frba.dds.main;

import ar.edu.utn.frba.dds.models.entities.usuarios.Rol;
import ar.edu.utn.frba.dds.models.entities.usuarios.TipoRol;
import ar.edu.utn.frba.dds.models.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.models.factories.sending_strategy.SendingStrategyFactory;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.time.chrono.IsoEra;
import java.util.List;

public class MainDB implements WithSimplePersistenceUnit {
    private EntityManager entityManager;


    public static void main(String[] args) throws IOException {

        MainDB db = new MainDB();
        db.entityManager = db.entityManager();
        db.impactarEnBase();
        ServiceLocator serviceLocator = ServiceLocator.getInstance();

        Usuario user = new Usuario("test", "Pedrito1213311@", List.of(TipoRol.HUMANO));
        user.setStrategiaDeEnvio(SendingStrategyFactory.create("TELEGRAM"));
        db.guardarUsuario(user);

    }

    public void impactarEnBase(){
        withTransaction(() ->{

        });
    }

    public void guardarUsuario(Usuario user){
        beginTransaction();
        entityManager.persist(user);
        commitTransaction();
    }
}
