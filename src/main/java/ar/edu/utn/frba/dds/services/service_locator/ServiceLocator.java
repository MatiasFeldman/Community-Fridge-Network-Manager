package ar.edu.utn.frba.dds.services.service_locator;

import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail.MimeMailSender;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.telegram.TelegramSender;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.whatsapp.WhatsAppSender;
import ar.edu.utn.frba.dds.models.repositories.distribuciones_de_viandas.DistribucionesDeViandasRepository;
import ar.edu.utn.frba.dds.models.repositories.donaciones_de_vianda.DonacionesDeViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.models.repositories.humanos.HumanosRepository;
import ar.edu.utn.frba.dds.models.repositories.incidentes.imp.IncidentesRepository;
import ar.edu.utn.frba.dds.models.repositories.personasVulnerables.PersonasVulnerablesRepository;

public class ServiceLocator {


    private MimeMailSender mimeMailSender;
    private TelegramSender telegramSender;
    private WhatsAppSender whatsAppSender;
    private HeladerasRepository heladeras;
    private IncidentesRepository incidentes;
    private HumanosRepository humanos;
    private PersonasVulnerablesRepository personasVulnerables;
    private DistribucionesDeViandasRepository distribucionesDeViandas;
    private DonacionesDeViandaRepository donacionesDeVianda;

    private static ServiceLocator instance = null;


    public static ServiceLocator getInstance() {
        if (instance == null) {
            instance = new ServiceLocator();
        }
        return instance;
    }

    public static MimeMailSender getMimeMailSender() {
        return instance.mimeMailSender;
    }

    public static TelegramSender getTelegramSender() {
        return instance.telegramSender;
    }

    public static void setMimeMailSender(MimeMailSender mimeMailSender) {
        instance.mimeMailSender = mimeMailSender;
    }

    public static void setTelegramSender(TelegramSender telegramSender) {
        instance.telegramSender = telegramSender;
    }

    public static HeladerasRepository getHeladerasRepository() {
        return instance.heladeras;
    }

    public static void setHeladerasRepository(HeladerasRepository heladeras) {
        instance.heladeras = heladeras;
    }

    public static IncidentesRepository getIncidentesRepository() {
        return instance.incidentes;
    }

    public static void setIncidentesRepository(IncidentesRepository incidentes) {
        instance.incidentes = incidentes;
    }

    public static HumanosRepository getHumanosRepository() {
        return instance.humanos;
    }

    public static void setHumanosRepository(HumanosRepository humanos) {
        instance.humanos = humanos;
    }

    public static PersonasVulnerablesRepository getPersonasVulnerablesRepository() {
        return instance.personasVulnerables;
    }

    public static void setPersonasVulnerablesRepository(PersonasVulnerablesRepository personasVulnerables) {
        instance.personasVulnerables = personasVulnerables;
    }

    public static WhatsAppSender getWhatsAppSender() {
        return instance.whatsAppSender;
    }

    public static void setWhatsAppSender(WhatsAppSender whatsAppSender) {
        instance.whatsAppSender = whatsAppSender;
    }

    public static DistribucionesDeViandasRepository getDistribucionesDeViandasRepository() {
        return instance.distribucionesDeViandas;
    }

    public static void setDistribucionesDeViandasRepository(DistribucionesDeViandasRepository distribucionesDeViandas) {
        instance.distribucionesDeViandas = distribucionesDeViandas;
    }

    public static DonacionesDeViandaRepository getDonacionesDeViandaRepository() {
        return instance.donacionesDeVianda;
    }

    public static void setDonacionesDeViandaRepository(DonacionesDeViandaRepository donacionesDeVianda) {
        instance.donacionesDeVianda = donacionesDeVianda;
    }
}
