package ar.edu.utn.frba.dds.models.main;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;

public class MainConexion {

    public static void main(String[] args){
        HeladerasRepository heladeras = ServiceLocator.getHeladerasRepository();

        heladeras
                .buscarTodos()
                .forEach(Heladera::evaluarConexion);
    }
}
