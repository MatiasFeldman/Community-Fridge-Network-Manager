package ar.edu.utn.frba.dds.services.service_locator;

import ar.edu.utn.frba.dds.controllers.HeladerasController;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Accionador;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail.MimeMailSender;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.telegram.TelegramSender;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.whatsapp.WhatsAppSender;
import ar.edu.utn.frba.dds.models.repositories.distribuciones_de_viandas.DistribucionesDeViandasRepository;
import ar.edu.utn.frba.dds.models.repositories.distribuciones_de_viandas.dao.DistribucionesDeViandasCollection;
import ar.edu.utn.frba.dds.models.repositories.distribuciones_de_viandas.dao.DistribucionesDeViandasDataBase;
import ar.edu.utn.frba.dds.models.repositories.donaciones_de_vianda.DonacionesDeViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.donaciones_de_vianda.dao.DonacionesDeViandaCollection;
import ar.edu.utn.frba.dds.models.repositories.donaciones_de_vianda.dao.DonacionesDeViandaDataBase;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasCollection;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasDataBase;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.dao.HumanosCollection;
import ar.edu.utn.frba.dds.models.repositories.humanos.dao.HumanosDataBase;
import ar.edu.utn.frba.dds.models.repositories.incidentes.dao.IncidentesCollection;
import ar.edu.utn.frba.dds.models.repositories.incidentes.dao.IncidentesDataBase;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import ar.edu.utn.frba.dds.models.repositories.intentos_de_apertura.IntentosDeAperturaCollection;
import ar.edu.utn.frba.dds.models.repositories.intentos_de_apertura.IntentosDeAperturaDataBase;
import ar.edu.utn.frba.dds.models.repositories.intentos_de_apertura.IntentosDeAperturaRepository;
import ar.edu.utn.frba.dds.models.repositories.juridicas.JuridicasRepository;
import ar.edu.utn.frba.dds.models.repositories.juridicas.dao.JuridicasCollection;
import ar.edu.utn.frba.dds.models.repositories.personasVulnerables.PersonasVulnerablesRepository;
import ar.edu.utn.frba.dds.models.repositories.personasVulnerables.dao.PersonasVulnerablesCollection;
import ar.edu.utn.frba.dds.models.repositories.personasVulnerables.dao.PersonasVulnerablesDataBase;
import ar.edu.utn.frba.dds.models.repositories.solicitudes_de_apertura_de_heladera.SolicitudesDeAperturaRepository;
import ar.edu.utn.frba.dds.models.repositories.solicitudes_de_apertura_de_heladera.dao.SolicitudDeAperturaCollection;
import ar.edu.utn.frba.dds.models.repositories.solicitudes_de_apertura_de_heladera.dao.SolicitudDeAperturaDB;
import ar.edu.utn.frba.dds.models.repositories.tarjetas_colaboradores.TarjetasColaboradoresRepository;
import ar.edu.utn.frba.dds.models.repositories.tarjetas_colaboradores.dao.TarjetasColaboradoresCollection;
import ar.edu.utn.frba.dds.models.repositories.tarjetas_colaboradores.dao.TarjetasColaboradoresDB;
import ar.edu.utn.frba.dds.utils.server.PrettyProperties;

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
        String persistence = PrettyProperties.getInstance().propertyFromName("persistence");

        if (componentName.equals(Accionador.class.getName())) {
            Accionador accionador = new Accionador();
            instances.put(componentName, accionador);
        } else if (componentName.equals(MimeMailSender.class.getName())) {
            MimeMailSender mailSender = new MimeMailSender();
            instances.put(componentName, mailSender);
        } else if (componentName.equals(TelegramSender.class.getName())) {
            TelegramSender telegramSender = new TelegramSender();
            instances.put(componentName, telegramSender);
        } else if (componentName.equals(WhatsAppSender.class.getName())) {
            WhatsAppSender whatsAppSender = new WhatsAppSender();
            instances.put(componentName, whatsAppSender);
        } else if (componentName.equals(JuridicasRepository.class.getName())) {
            JuridicasRepository juridicas = new JuridicasRepository(new JuridicasCollection(new ArrayList<>()));
            instances.put(componentName, juridicas);
        }

        if (persistence.equals("memory")) {
            if (!instances.containsKey(componentName)) {
                if (componentName.equals(HeladerasController.class.getName())) {
                    HeladerasController controller = new HeladerasController(
                            instanceOf(Accionador.class),
                            instanceOf(SolicitudesDeAperturaRepository.class),
                            instanceOf(IntentosDeAperturaRepository.class),
                            instanceOf(TarjetasColaboradoresRepository.class),
                            instanceOf(HeladerasRepository.class), instanceOf(HumanosRepository.class),
                            instanceOf(JuridicasRepository.class));
                    instances.put(componentName, controller);
                } else if (componentName.equals(TarjetasColaboradoresRepository.class.getName())) {
                    TarjetasColaboradoresRepository tarjetas = new TarjetasColaboradoresRepository(new TarjetasColaboradoresCollection(new ArrayList<>()));
                    instances.put(componentName, tarjetas);
                } else if (componentName.equals(DistribucionesDeViandasRepository.class.getName())) {
                    DistribucionesDeViandasRepository instance = new DistribucionesDeViandasRepository(new DistribucionesDeViandasCollection(new ArrayList<>()));
                    instances.put(componentName, instance);
                } else if (componentName.equals(SolicitudesDeAperturaRepository.class.getName())) {
                    SolicitudesDeAperturaRepository solicitudes = new SolicitudesDeAperturaRepository(new SolicitudDeAperturaCollection(new ArrayList<>()));
                    instances.put(componentName, solicitudes);
                } else if (componentName.equals(IntentosDeAperturaRepository.class.getName())) {
                    IntentosDeAperturaRepository intentos = new IntentosDeAperturaRepository(new IntentosDeAperturaCollection(new ArrayList<>()));
                    instances.put(componentName, intentos);
                } else if (componentName.equals(Accionador.class.getName())) {
                    Accionador accionador = new Accionador();
                    instances.put(componentName, accionador);
                } else if (componentName.equals(HeladerasRepository.class.getName())) {
                    HeladerasRepository heladeras = new HeladerasRepository(new HeladerasCollection(new ArrayList<>()));
                    instances.put(componentName, heladeras);
                } else if (componentName.equals(HumanosRepository.class.getName())) {
                    HumanosRepository humanos = new HumanosRepository(new HumanosCollection(new ArrayList<>()));
                    instances.put(componentName, humanos);
                } else if (componentName.equals(PersonasVulnerablesRepository.class.getName())) {
                    PersonasVulnerablesRepository personasVulnerables = new PersonasVulnerablesRepository(new PersonasVulnerablesCollection(new ArrayList<>()));
                    instances.put(componentName, personasVulnerables);
                } else if (componentName.equals(DonacionesDeViandaRepository.class.getName())) {
                    DonacionesDeViandaRepository donaciones = new DonacionesDeViandaRepository(new DonacionesDeViandaCollection(new ArrayList<>()));
                    instances.put(componentName, donaciones);
                } else if (componentName.equals(IncidentesRepository.class.getName())) {
                    IncidentesRepository incidentes = new IncidentesRepository(new IncidentesCollection(new ArrayList<>()));
                    instances.put(componentName, incidentes);
                }
            }

        } else if (persistence.equals("sql")) {
            if (!instances.containsKey(componentName)) {
                if (componentName.equals(HeladerasController.class.getName())) {
                    HeladerasController controller = new HeladerasController(
                            instanceOf(Accionador.class),
                            instanceOf(SolicitudesDeAperturaRepository.class),
                            instanceOf(IntentosDeAperturaRepository.class),
                            instanceOf(TarjetasColaboradoresRepository.class),
                            instanceOf(HeladerasRepository.class), instanceOf(HumanosRepository.class),
                            instanceOf(JuridicasRepository.class));
                    instances.put(componentName, controller);
                } else if (componentName.equals(TarjetasColaboradoresRepository.class.getName())) {
                    TarjetasColaboradoresRepository tarjetas = new TarjetasColaboradoresRepository(new TarjetasColaboradoresDB());
                    instances.put(componentName, tarjetas);
                } else if (componentName.equals(DistribucionesDeViandasRepository.class.getName())) {
                    DistribucionesDeViandasRepository instance = new DistribucionesDeViandasRepository(new DistribucionesDeViandasDataBase());
                    instances.put(componentName, instance);
                } else if (componentName.equals(SolicitudesDeAperturaRepository.class.getName())) {
                    SolicitudesDeAperturaRepository solicitudes = new SolicitudesDeAperturaRepository(new SolicitudDeAperturaDB());
                    instances.put(componentName, solicitudes);
                } else if (componentName.equals(IntentosDeAperturaRepository.class.getName())) {
                    IntentosDeAperturaRepository intentos = new IntentosDeAperturaRepository(new IntentosDeAperturaDataBase());
                    instances.put(componentName, intentos);
                }
            } else if (componentName.equals(HeladerasRepository.class.getName())) {
                HeladerasRepository heladeras = new HeladerasRepository(new HeladerasDataBase());
                instances.put(componentName, heladeras);
            } else if (componentName.equals(HumanosRepository.class.getName())) {
                HumanosRepository humanos = new HumanosRepository(new HumanosDataBase());
                instances.put(componentName, humanos);
            } else if (componentName.equals(PersonasVulnerablesRepository.class.getName())) {
                PersonasVulnerablesRepository personasVulnerables = new PersonasVulnerablesRepository(new PersonasVulnerablesDataBase());
                instances.put(componentName, personasVulnerables);
            } else if (componentName.equals(DonacionesDeViandaRepository.class.getName())) {
                DonacionesDeViandaRepository donaciones = new DonacionesDeViandaRepository(new DonacionesDeViandaDataBase());
                instances.put(componentName, donaciones);
            } else if (componentName.equals(IncidentesRepository.class.getName())) {
                IncidentesRepository incidentes = new IncidentesRepository(new IncidentesDataBase());
                instances.put(componentName, incidentes);
            }

        }


        return (T) instances.get(componentName);
    }
}
