package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dtos.heladeras.HeladeraOutputDTO;
import ar.edu.utn.frba.dds.models.entities.colaboraciones.Rubro;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.entities.ubicacion.GeoRefDeDirecc;
import ar.edu.utn.frba.dds.models.entities.ubicacion.LugarDonacion;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.models.repositories.rubros.RubrosRepository;
import ar.edu.utn.frba.dds.services.api_integracion.APIAdapter;
import ar.edu.utn.frba.dds.services.api_integracion.ApiIntegracionGrupo1;
import ar.edu.utn.frba.dds.services.georef.GobiernoAPI;
import ar.edu.utn.frba.dds.services.georef.IGeoRefApi;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import ar.edu.utn.frba.dds.utils.RenderUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ViewsController {

    public static void landing(Context ctx) {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Inicio");

        RenderUtils.renderizar(ctx,"landing.hbs",model);
    }

    public static void colaborar(Context ctx) {
        Map<String, Object> model = new HashMap<>();
        List<String> rolesUsuario = ctx.sessionAttribute("roles");
        String roleUsuario = rolesUsuario.get(0);
        model.put("titulo", "Colaborar");
        model.put("rol", roleUsuario);

        RenderUtils.renderizar(ctx,"colaborar.hbs", model);
    }

    public static void formDonarDinero(Context ctx) {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Donar dinero");

        RenderUtils.renderizar(ctx,"colaboraciones/dinero.hbs", model);
    }

    public static void formDistribuirViandas(Context ctx) {
        HeladerasRepository heladerasRepository = ServiceLocator.instanceOf(HeladerasRepository.class);
        List<Heladera> heladeras = heladerasRepository.buscarTodos();

        heladeras.forEach(heladera -> {
            String mensajeDisponibilidad;
            int cantActual = heladera.cantActual();

            if (cantActual == 0) {
                mensajeDisponibilidad = "¡Sin viandas disponibles!";
            } else if (cantActual <= 4) {
                mensajeDisponibilidad = "¡Quedan menos de 5 viandas!";
            } else if (cantActual <= 10) {
                mensajeDisponibilidad = "¡Quedan menos de 10 viandas disponibles!";
            } else if (cantActual <= 20) {
                mensajeDisponibilidad = "Stock moderado de viandas";
            } else {
                mensajeDisponibilidad = "Suficiente stock de viandas";
            }

            heladera.setMensajeDisponiblididad(mensajeDisponibilidad);
        });

        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Distribuir viandas");
        model.put("heladeras", heladeras);

        RenderUtils.renderizar(ctx,"colaboraciones/distribucion-de-viandas.hbs", model);
    }

    public static void formDonarViandas(Context ctx) {
        HeladerasRepository heladerasRepository = ServiceLocator.instanceOf(HeladerasRepository.class);
        List<Heladera> heladeras = heladerasRepository.buscarTodos();

        // Filtrar heladeras con capActual > 0
        List<Heladera> heladerasDisponibles = heladeras.stream()
                .filter(heladera -> heladera.getCapActual() > 0)
                .collect(Collectors.toList());

        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Donar viandas");
        model.put("heladeras", heladerasDisponibles);

        RenderUtils.renderizar(ctx,"colaboraciones/donacion-de-viandas.hbs", model);
    }

    public static void formHeladeraACargo(Context ctx) {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Hacerse cargo de heladera");


        RenderUtils.renderizar(ctx,"colaboraciones/heladera-a-cargo.hbs", model);
    }

    public static void formRegistroPersonaVulnerable(Context ctx) {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Registro de persona vulnerable");

        RenderUtils.renderizar(ctx,"colaboraciones/registro-vulnerable.hbs", model);
    }

    public static void formCargaMasiva(Context ctx) {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Carga masiva");

        RenderUtils.renderizar(ctx,"colaboraciones/carga_masiva.hbs", model);
    }

    public static void formRegistrarOferta(Context ctx) {
        Map<String, Object> model = new HashMap<>();
        List<Rubro> rubros;
        model.put("titulo", "Registro de oferta");
        rubros = ServiceLocator.instanceOf(RubrosRepository.class).buscarTodos();
        model.put("rubros", rubros);

        RenderUtils.renderizar(ctx,"colaboraciones/ofertar.hbs", model);
    }


    public static void formLogin(Context ctx){

        // Verifica si ya hay un usuario autenticado en la sesión
        if (ctx.sessionAttribute("user") != null) {
            ctx.redirect("/");
            return;
        }

        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Login");

        RenderUtils.renderizar(ctx,"login.hbs", model);
    }

    public static void formRegistro(Context ctx) {

        if (ctx.sessionAttribute("user") != null) {
            ctx.redirect("/");
            return;
        }

        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Registro");

        RenderUtils.renderizar(ctx,"registro-usuario/registro-tipo.hbs", model);
    }



    public static void formFallaTecnica(Context ctx) {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Reporte falla ténica");

        String tipoBusqueda = ctx.queryParam("busqueda");
        String valorBusqueda = ctx.queryParam("valor");
        List<HeladeraOutputDTO> dtos = busquedaDeHeladeras(tipoBusqueda,valorBusqueda);

        model.put("heladeras", dtos);


        RenderUtils.renderizar(ctx,"heladeras/fallas-tecnicas.hbs", model);
    }

    private static List<HeladeraOutputDTO> busquedaDeHeladeras(String tipoBusqueda, String valorBusqueda){
        List<Heladera> heladeras = new ArrayList<>();
        List<HeladeraOutputDTO> dtos = new ArrayList<>();
        if (tipoBusqueda == null || (valorBusqueda == null && !tipoBusqueda.equalsIgnoreCase("todas"))) {
            heladeras = new ArrayList<>();
        } else {
            heladeras = switch (tipoBusqueda) {
                case "direccion" ->
                        ServiceLocator.instanceOf(HeladerasRepository.class).buscarHeladerasPorDireccion(valorBusqueda);
                case "comuna" -> ServiceLocator.instanceOf(HeladerasRepository.class).buscarPorComuna(valorBusqueda);
                case "todas" -> ServiceLocator.instanceOf(HeladerasRepository.class).buscarTodos();
                default -> heladeras;
            };
        }
        heladeras.forEach(h -> dtos.add(HeladeraOutputDTO.of(h)));
        return dtos;
    }

    public static void formAlertas(Context ctx) {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Incidentes");

        String tipoBusqueda = ctx.queryParam("busqueda");
        String valorBusqueda = ctx.queryParam("valor");
        List<HeladeraOutputDTO> dtos = busquedaDeHeladeras(tipoBusqueda,valorBusqueda);

        model.put("heladeras", dtos);


        RenderUtils.renderizar(ctx,"heladeras/alertas.hbs", model);
    }

    public static void cargaCsv(Context context) {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Carga Csv");

        RenderUtils.renderizar(context,"carga-csv.hbs", model);
    }

    public static void reportesHeladerasInicio(Context context) {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Reportes de heladeras");

        RenderUtils.renderizar(context,"reportes/main-reportes.hbs", model);
    }

    public static void notFound(Context context) {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Error 404");

        RenderUtils.renderizar(context,"404.hbs", model);
    }

    public static void badRequest(Context context) {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Error 400");

        RenderUtils.renderizar(context,"400.hbs", model);
    }

    public static void dondeDonar(Context context) {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Donde donar");

        RenderUtils.renderizar(context,"donde-donar.hbs", model);
    }

    @SneakyThrows
    public static void dondeDonarMapa(Context context) {
        String direccion = context.formParam("direccion");
        IGeoRefApi apiGeoRef = ServiceLocator.instanceOf(GobiernoAPI.class);
        GeoRefDeDirecc geoRefDeDirecc = apiGeoRef.getCoord(direccion);
        APIAdapter apiLugaresCercanos = ServiceLocator.instanceOf(ApiIntegracionGrupo1.class);
        List<LugarDonacion> lugares = apiLugaresCercanos.getLugaresCercanos(geoRefDeDirecc.getCoords());

        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Donde donar");
        model.put("direccion", direccion);
        model.put("latitud", geoRefDeDirecc.getCoords().getLatitud());
        model.put("longitud", geoRefDeDirecc.getCoords().getLongitud());

        ObjectMapper objectMapper = new ObjectMapper();
        /*for (LugarDonacion lugar : lugares) {
            System.out.println(lugar.getNombre());
            System.out.println(lugar.getDireccion());
            System.out.println(lugar.getCoordenadas().getLatitud());
            System.out.println(lugar.getCoordenadas().getLongitud());
        }*/
        String lugaresJson = objectMapper.writeValueAsString(lugares);
        model.put("lugares", lugaresJson);
        model.put("lugaresDTO", lugares);
        RenderUtils.renderizar(context,"donde-donar-resultados.hbs", model);
    }

    public static void confirmacionColaboracion(Context ctx) {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Colaboración confirmada");

        RenderUtils.renderizar(ctx,"colaboraciones/confirmacion-colaboracion.hbs", model);
    }

    public static void viewImage(Context ctx) {
        // Obtener los parámetros de la ruta
        String nombreCarpeta = ctx.pathParam("nombreCarpeta");
        String nombreArchivo = ctx.pathParam("nombreArchivo");


        File archivo = new File("src/main/java/ar/edu/utn/frba/dds/imagenesDinamicas/" + nombreCarpeta + "/" + nombreArchivo);


        if (archivo.exists()) {

            String mimeType = "image/jpeg";
            ctx.contentType(mimeType);

            try {
                ctx.result(new FileInputStream(archivo));
            } catch (FileNotFoundException e) {
                ctx.status(500).result("Error al cargar la imagen.");
            }
        } else {

            ctx.status(404).result("Imagen no encontrada");
        }
    }
}


