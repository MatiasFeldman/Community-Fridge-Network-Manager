package ar.edu.utn.frba.dds.services.service_locator;

import ar.edu.utn.frba.dds.controllers.*;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Accionador;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.mail.MimeMailSender;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.telegram.TelegramSender;
import ar.edu.utn.frba.dds.models.entities.helpers.mensajeria.whatsapp.WhatsAppSender;
import ar.edu.utn.frba.dds.models.entities.helpers.reportes.PDFgenerator;
import ar.edu.utn.frba.dds.models.entities.ubicacion.RecomendarPuntos;
import ar.edu.utn.frba.dds.models.repositories.atributos_humano.AtributosHumanoRepository;
import ar.edu.utn.frba.dds.models.repositories.atributos_humano.dao.AtributosHumanoCollection;
import ar.edu.utn.frba.dds.models.repositories.atributos_humano.dao.AtributosHumanoDataBase;
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
import ar.edu.utn.frba.dds.models.repositories.ofertas.OfertasRepository;
import ar.edu.utn.frba.dds.models.repositories.ofertas.dao.OfertasCollection;
import ar.edu.utn.frba.dds.models.repositories.ofertas.dao.OfertasDataBase;
import ar.edu.utn.frba.dds.models.repositories.personasVulnerables.PersonasVulnerablesRepository;
import ar.edu.utn.frba.dds.models.repositories.personasVulnerables.dao.PersonasVulnerablesCollection;
import ar.edu.utn.frba.dds.models.repositories.personasVulnerables.dao.PersonasVulnerablesDataBase;
import ar.edu.utn.frba.dds.models.repositories.rubros.RubrosRepository;
import ar.edu.utn.frba.dds.models.repositories.rubros.dao.RubroCollection;
import ar.edu.utn.frba.dds.models.repositories.rubros.dao.RubroDataBase;
import ar.edu.utn.frba.dds.models.repositories.solicitudes_de_apertura_de_heladera.SolicitudesDeAperturaRepository;
import ar.edu.utn.frba.dds.models.repositories.solicitudes_de_apertura_de_heladera.dao.SolicitudDeAperturaCollection;
import ar.edu.utn.frba.dds.models.repositories.solicitudes_de_apertura_de_heladera.dao.SolicitudDeAperturaDB;
import ar.edu.utn.frba.dds.models.repositories.tarjetas_colaboradores.TarjetasColaboradoresRepository;
import ar.edu.utn.frba.dds.models.repositories.tarjetas_colaboradores.dao.TarjetasColaboradoresCollection;
import ar.edu.utn.frba.dds.models.repositories.tarjetas_colaboradores.dao.TarjetasColaboradoresDB;
import ar.edu.utn.frba.dds.models.repositories.usuarios.UsuariosRepository;
import ar.edu.utn.frba.dds.models.repositories.usuarios.dao.UsuariosCollection;
import ar.edu.utn.frba.dds.models.repositories.usuarios.dao.UsuariosDataBase;
import ar.edu.utn.frba.dds.services.georef.GobiernoAPI;
import ar.edu.utn.frba.dds.utils.seguridad.*;
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

        if (!instances.containsKey(componentName)) {
            if (componentName.equals(Accionador.class.getName())) {
                Accionador accionador = new Accionador();
                instances.put(componentName, accionador);
            } else if (componentName.equals(ReportesController.class.getName())) {
                ReportesController reportesController = new ReportesController();
                instances.put(componentName, reportesController);
            } else if (componentName.equals(UsuariosController.class.getName())) {
                UsuariosController usuariosController = new UsuariosController();
                instances.put(componentName, usuariosController);
            } else if (componentName.equals(PDFgenerator.class.getName())) {
                PDFgenerator pdfGenerator = new PDFgenerator();
                instances.put(componentName, pdfGenerator);
            } else if (componentName.equals(GobiernoAPI.class.getName())) {
                GobiernoAPI api = new GobiernoAPI();
                instances.put(componentName, api);
            } else if (componentName.equals(ValidadorDeContrasenias.class.getName())) {
                ValidadorDeContrasenias validador = new ValidadorDeContrasenias();
                validador.agregarCondiciones(new CumpleLongitud(8, 64),
                        new TieneMayuscula(),
                        new TieneMinuscula(),
                        new TieneNumero(),
                        new TieneCaracterEspecial(),
                        new NoEstaDentroDeLasComunes());
                instances.put(componentName, validador);
            } else if (componentName.equals(MimeMailSender.class.getName())) {
                MimeMailSender mailSender = new MimeMailSender();
                instances.put(componentName, mailSender);
            } else if (componentName.equals(TelegramSender.class.getName())) {
                TelegramSender telegramSender = new TelegramSender();
                instances.put(componentName, telegramSender);
            } else if (componentName.equals(WhatsAppSender.class.getName())) {
                WhatsAppSender whatsAppSender = new WhatsAppSender();
                instances.put(componentName, whatsAppSender);
            } else if (componentName.equals(JuridicasRepository.class.getName())) {//mepa que esta mal que este aca
                JuridicasRepository juridicas = new JuridicasRepository(new JuridicasCollection(new ArrayList<>()));
                instances.put(componentName, juridicas);
            } else if (componentName.equals(HumanosController.class.getName())) {
                HumanosController humanos = new HumanosController();
                instances.put(componentName, humanos);
            } else if (componentName.equals(JuridicasController.class.getName())) {
                JuridicasController juridicas = new JuridicasController();
                instances.put(componentName, juridicas);
            }else if (componentName.equals(RecomendarPuntos.class.getName())) {
                RecomendarPuntos recomendarPuntos = new RecomendarPuntos();
                instances.put(componentName, recomendarPuntos);
            }else if (componentName.equals(OfertasController.class.getName())) {
                OfertasController oferta = new OfertasController();
                instances.put(componentName, oferta);
            }

            if (persistence.equals("memory")) {

                if (componentName.equals(HeladerasController.class.getName())) {
                    HeladerasController controller = new HeladerasController(
                            instanceOf(Accionador.class),
                            instanceOf(SolicitudesDeAperturaRepository.class),
                            instanceOf(IntentosDeAperturaRepository.class),
                            instanceOf(TarjetasColaboradoresRepository.class),
                            instanceOf(HeladerasRepository.class), instanceOf(HumanosRepository.class),
                            instanceOf(JuridicasRepository.class));
                    instances.put(componentName, controller);
                } else if (componentName.equals(AtributosHumanoRepository.class.getName())) {
                    AtributosHumanoRepository atributos = new AtributosHumanoRepository(new AtributosHumanoCollection(new ArrayList<>()));
                    instances.put(componentName, atributos);
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
                    Accionador accionador = Accionador.of(ServiceLocator.instanceOf(IncidentesRepository.class));
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
                }else if (componentName.equals(JuridicasRepository.class.getName())) {
                    JuridicasRepository humanos = new JuridicasRepository(new JuridicasCollection(new ArrayList<>()));
                    instances.put(componentName, humanos);
                }else if (componentName.equals(UsuariosRepository.class.getName())) {
                    UsuariosRepository usuarios = new UsuariosRepository(new UsuariosCollection(new ArrayList<>()));
                    instances.put(componentName, usuarios);
                }else if (componentName.equals(OfertasRepository.class.getName())) {
                    OfertasRepository oferta = new OfertasRepository(new OfertasCollection());
                    instances.put(componentName, oferta);
                }else if (componentName.equals(RubrosRepository.class.getName())) {
                    RubrosRepository rubro = new RubrosRepository(new RubroCollection(new ArrayList<>()));
                    instances.put(componentName, rubro);
                }

            } else if (persistence.equals("sql")) {
                if (componentName.equals(HeladerasController.class.getName())) {
                    HeladerasController controller = new HeladerasController(
                            instanceOf(Accionador.class),
                            instanceOf(SolicitudesDeAperturaRepository.class),
                            instanceOf(IntentosDeAperturaRepository.class),
                            instanceOf(TarjetasColaboradoresRepository.class),
                            instanceOf(HeladerasRepository.class), instanceOf(HumanosRepository.class),
                            instanceOf(JuridicasRepository.class));
                    instances.put(componentName, controller);
                } else if (componentName.equals(AtributosHumanoRepository.class.getName())) {
                    AtributosHumanoRepository atributos = new AtributosHumanoRepository(new AtributosHumanoDataBase());
                    instances.put(componentName, atributos);
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
            }else if (componentName.equals(UsuariosRepository.class.getName())) {
                UsuariosRepository usuarios = new UsuariosRepository(new UsuariosDataBase());
                instances.put(componentName, usuarios);
            }else if (componentName.equals(OfertasRepository.class.getName())) {
                OfertasRepository oferta = new OfertasRepository(new OfertasDataBase());
                instances.put(componentName, oferta);
            }else if (componentName.equals(RubrosRepository.class.getName())) {
                RubrosRepository rubro = new RubrosRepository(new RubroDataBase());
                instances.put(componentName, rubro);
            }

        }

        return (T) instances.get(componentName);
    }
}
