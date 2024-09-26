package ar.edu.utn.frba.dds.services.service_locator;

import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail.MimeMailSender;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.telegram.TelegramSender;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.whatsapp.WhatsAppSender;
import ar.edu.utn.frba.dds.models.repositories.distribuciones_de_viandas.DistribucionesDeViandasRepository;
import ar.edu.utn.frba.dds.models.repositories.donaciones_de_vianda.DonacionesDeViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.donaciones_de_vianda.dao.DonacionesDeViandaCollection;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import ar.edu.utn.frba.dds.models.repositories.personasVulnerables.PersonasVulnerablesRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {

    private static ServiceLocator instance = null;

    private static Map<String, Object> instances = new HashMap<>();


    public static ServiceLocator getInstance() {
        if (instance == null) {
            instance = new ServiceLocator();
        }
        return instance;
    }


    @SuppressWarnings("unchecked")
    public static <T> T instanceOf(Class<T> componentClass) {
        String componentName = componentClass.getName();

        if (!instances.containsKey(componentName)) {
            if (componentName.equals(DistribucionesDeViandasRepository.class.getName())) {
                DistribucionesDeViandasRepository instance = new DistribucionesDeViandasRepository(new);
                instances.put(componentName, instance);
            } else {
                throw new RuntimeException("No se encontro el componente");
            }
        }

        return (T) instances.get(componentName);
    }
}
