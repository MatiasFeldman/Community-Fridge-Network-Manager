package ar.edu.utn.frba.dds.utils.server;

import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;

public class Initializer {
    public static void init(){
        HeladerasRepository heladeras = ServiceLocator.instanceOf(HeladerasRepository.class);

        Direccion d1 = Direccion
                .of("Calle Falsa", 123);

        Direccion d2 = Direccion
                .of("Calle Falsa2", 124);

        Direccion d3 = Direccion.of("Calle Falsa3", 125);

        Heladera h1 = Heladera
                .builder()
                .nombre("Heladera 1")
                .direccion(d1)
                .capacidadMaxima(100)
                .capActual(100)
                .activa(true)
                .build();

        Heladera h2 = Heladera
                .builder()
                .nombre("Heladera 2")
                .direccion(d2)
                .capacidadMaxima(100)
                .capActual(100)
                .activa(true)
                .build();

        Heladera h3 = Heladera
                .builder()
                .nombre("Heladera 3")
                .direccion(d3)
                .capacidadMaxima(100)
                .capActual(100)
                .activa(true)
                .build();

        heladeras.guardar(h1);
        heladeras.guardar(h2);
        heladeras.guardar(h3);
    }
}
