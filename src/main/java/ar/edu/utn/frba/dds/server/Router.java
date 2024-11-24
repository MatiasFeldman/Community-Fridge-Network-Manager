package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.controllers.*;
import ar.edu.utn.frba.dds.models.entities.usuarios.TipoRol;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import io.javalin.Javalin;

import java.io.File;
import java.io.FileInputStream;


public class Router {
    public static void init(Javalin app) {
        // Paginas generales
        app.get("/", ViewsController::landing);
        app.get("/login", ViewsController::formLogin);
        app.post("/login", ctx -> ServiceLocator.instanceOf(UsuariosController.class).handleLogin(ctx));
        app.post("/logout", ctx -> ServiceLocator.instanceOf(UsuariosController.class).handleLogout(ctx));

        // perfil
        app.get("/perfil", ServiceLocator.instanceOf(UsuariosController.class)::handlePerfil);
        app.post("/perfil", ServiceLocator.instanceOf(UsuariosController.class)::handleUpdate);

        // Registro de usuarios
        app.get("/registro", ViewsController::formRegistro);

        app.get("/registro/juridica", ServiceLocator.instanceOf(JuridicasController.class)::create);
        app.post("/registro/juridica", ServiceLocator.instanceOf(JuridicasController.class)::save);

        app.get("/registro/humano", ctx -> ServiceLocator.instanceOf(HumanosController.class).formRegistroHumano(ctx));
        app.post("/registro/humano", ctx -> ServiceLocator.instanceOf(HumanosController.class).save(ctx));

        app.get("/registro/tecnico", ServiceLocator.instanceOf(TecnicosController.class)::create);
        app.post("/registro/tecnico", ServiceLocator.instanceOf(TecnicosController.class)::save);

        app.get("/registro/modificar-registro-humano", ctx -> ServiceLocator.instanceOf(HumanosController.class).camposFormHumano(ctx), TipoRol.ADMIN);

        // colaboraciones
        app.get("/colaborar", ViewsController::colaborar, TipoRol.ADMIN,TipoRol.HUMANO, TipoRol.JURIDICA);

        app.get("/colaborar/donar-dinero", ViewsController::formDonarDinero,TipoRol.HUMANO, TipoRol.JURIDICA);
        app.post("/colaborar/donar-dinero", ctx -> ServiceLocator.instanceOf(ContribucionesController.class).crearDonacionDeDinero(ctx), TipoRol.JURIDICA, TipoRol.HUMANO);

        app.get("/colaborar/distribuir-viandas", ViewsController::formDistribuirViandas, TipoRol.HUMANO);
        app.post("/colaborar/distribuir-viandas", ctx -> ServiceLocator.instanceOf(ContribucionesController.class).crearDistribucionDeViandas(ctx), TipoRol.HUMANO);

        app.get("/colaborar/donar-viandas", ViewsController::formDonarViandas, TipoRol.HUMANO);
        app.post("/colaborar/donar-viandas", ctx -> ServiceLocator.instanceOf(ContribucionesController.class).crearDonacionDeViandas(ctx), TipoRol.HUMANO);

        app.get("/colaborar/heladera-a-cargo", ViewsController::formHeladeraACargo, TipoRol.JURIDICA);
        app.post("/colaborar/heladera-a-cargo", ctx -> ServiceLocator.instanceOf(ContribucionesController.class).registrarHeladeraACargo(ctx), TipoRol.JURIDICA);

        app.get("/colaborar/registro-persona-vulnerable", ViewsController::formRegistroPersonaVulnerable, TipoRol.JURIDICA);
        app.post("/colaborar/registro-persona-vulnerable", ctx -> ServiceLocator.instanceOf(ContribucionesController.class).registrarPersonaVulnerable(ctx), TipoRol.JURIDICA);

        app.get("/colaborar/ofertar", ViewsController::formRegistrarOferta, TipoRol.JURIDICA);
        app.post("/colaborar/ofertar", ctx -> ServiceLocator.instanceOf(ContribucionesController.class).registrarOferta(ctx), TipoRol.JURIDICA);

        app.get("/colaborar/carga-csv", ViewsController::formCargaMasiva);
        app.post("/colaborar/carga-csv", ctx -> ServiceLocator.instanceOf(ContribucionesController.class).cargaMasiva(ctx));

        app.post("/recomendar-puntos", ctx -> ServiceLocator.instanceOf(ContribucionesController.class).recomendarPuntos(ctx), TipoRol.JURIDICA);

        app.get("/confirmacion-colaboracion", ViewsController::confirmacionColaboracion, TipoRol.ADMIN, TipoRol.HUMANO, TipoRol.JURIDICA);


        // heladeras

        app.get("/heladeras", ctx -> ServiceLocator.instanceOf(HeladerasController.class).mostrarHeladeras(ctx));

        app.get("/heladeras/nueva", ctx -> ServiceLocator.instanceOf(HeladerasController.class).create(ctx));
        app.post("/heladeras/nueva", ctx -> ServiceLocator.instanceOf(HeladerasController.class).create(ctx));

        app.post("/heladeras/filtrar/estado", ctx -> ServiceLocator.instanceOf(HeladerasController.class).mostrarHeladeras(ctx));
        app.post("/heladeras/filtrar/busqueda", ctx -> ServiceLocator.instanceOf(HeladerasController.class).mostrarHeladeras(ctx));

        app.get("/heladeras/{id}/suscripcion/nueva", ctx -> ServiceLocator.instanceOf(HeladerasController.class).suscripcionView(ctx), TipoRol.ADMIN,TipoRol.HUMANO, TipoRol.JURIDICA);
        app.post("/suscribirse",ServiceLocator.instanceOf(HeladerasController.class)::suscribirse);
        app.post("/desuscribirse",ServiceLocator.instanceOf(HeladerasController.class)::desuscribirse);

        app.get("/heladeras/{id}/suscripciones", ctx -> ServiceLocator.instanceOf(HeladerasController.class).verSuscripcionesAHeladera(ctx), TipoRol.ADMIN,TipoRol.HUMANO, TipoRol.JURIDICA);

        app.get("/suscripciones", ctx -> ServiceLocator.instanceOf(HeladerasController.class).verSuscripcionesDeUsuario(ctx), TipoRol.ADMIN,TipoRol.HUMANO, TipoRol.JURIDICA);

        // incidentes y fallas

        app.get("/heladeras/reportar-falla-tecnica", ViewsController::formFallaTecnica, TipoRol.ADMIN,TipoRol.HUMANO, TipoRol.JURIDICA);
        app.post("/heladeras/reportar-falla-tecnica", ctx -> ServiceLocator.instanceOf(HeladerasController.class).registrarFallaTecnica(ctx), TipoRol.ADMIN,TipoRol.HUMANO, TipoRol.JURIDICA);

        app.get("/heladeras/reportar-falla-tecnica/{id}", ctx -> ServiceLocator.instanceOf(HeladerasController.class).reporteFallaTecnicaView(ctx), TipoRol.ADMIN,TipoRol.HUMANO, TipoRol.JURIDICA);
        app.get("/heladeras/{id}/reporte-falla-tecnica/nueva", ctx -> ServiceLocator.instanceOf(HeladerasController.class).reporteFallaTecnicaView(ctx), TipoRol.ADMIN,TipoRol.HUMANO, TipoRol.JURIDICA);

        app.get("/heladeras/incidentes",ViewsController::formAlertas,TipoRol.ADMIN,TipoRol.HUMANO, TipoRol.JURIDICA);
        app.get("/heladeras/{id}/incidentes", ctx -> ServiceLocator.instanceOf(ReportesController.class).detalleInicidenteView(ctx), TipoRol.ADMIN,TipoRol.HUMANO, TipoRol.JURIDICA);

        app.get("/heladeras/{id}/visita",ServiceLocator.instanceOf(TecnicosController.class)::formSolucionIncidente,TipoRol.ADMIN,TipoRol.TECNICO);
        app.post("/heladeras/{id}/visita",ServiceLocator.instanceOf(TecnicosController.class)::registrarVisita,TipoRol.TECNICO);


        app.get("/heladeras/{id}/visitas", ctx -> ServiceLocator.instanceOf(ReportesController.class).detalleVisitasView(ctx), TipoRol.ADMIN,TipoRol.HUMANO, TipoRol.JURIDICA);

        // modificar heladeras
        app.get("/heladeras/modificar", ctx -> ServiceLocator.instanceOf(HeladerasController.class).editarHeladera(ctx), TipoRol.ADMIN);
        app.delete("/heladeras/modificar", ctx -> ServiceLocator.instanceOf(HeladerasController.class).eliminarHeladera(ctx), TipoRol.ADMIN);
        app.put("/heladeras/modificar", ctx -> ServiceLocator.instanceOf(HeladerasController.class).modificarEstadoHeladera(ctx), TipoRol.ADMIN);
        app.post("/heladeras/modificar", ctx -> ServiceLocator.instanceOf(HeladerasController.class).actualizarHeladera(ctx), TipoRol.ADMIN);


        // reportes
        app.get("/heladeras/reportes", ViewsController::reportesHeladerasInicio, TipoRol.ADMIN);
        app.get("/heladeras/reportes/todos/download",ctx -> {ServiceLocator.instanceOf(ReportesController.class).generarReporteDeTodos(ctx);}, TipoRol.ADMIN);
        app.get("/heladeras/reportes/fallas/download",ctx -> {ServiceLocator.instanceOf(ReportesController.class).generarReporteDeFallas(ctx);}, TipoRol.ADMIN);
        app.get("/heladeras/reportes/donaciones/download",ctx -> {ServiceLocator.instanceOf(ReportesController.class).generarReporteDeViandasDonadas(ctx);}, TipoRol.ADMIN);
        app.get("/heladeras/reportes/movimiento/download",ctx -> {ServiceLocator.instanceOf(ReportesController.class).generarReporteDeMovimientoViandas(ctx);}, TipoRol.ADMIN);


        // ofertas

        app.get("/ofertas",  ctx ->  ServiceLocator.instanceOf(OfertasController.class).showOfertas(ctx), TipoRol.ADMIN, TipoRol.JURIDICA,TipoRol.HUMANO);
        app.get("/ofertas/{id}",  ctx ->  ServiceLocator.instanceOf(OfertasController.class).showOferta(ctx), TipoRol.ADMIN, TipoRol.JURIDICA,TipoRol.HUMANO);
        app.post("/oferta/{id}/canjear",  ServiceLocator.instanceOf(OfertasController.class)::canjearOferta,TipoRol.ADMIN, TipoRol.JURIDICA,TipoRol.HUMANO);

        //paginas de usuario
        app.get("/misCanjes",  ctx ->  ServiceLocator.instanceOf(ViewsController.class).viewMisCanjes(ctx), TipoRol.HUMANO, TipoRol.JURIDICA);
        app.get("/misVisitas",  ctx ->  ServiceLocator.instanceOf(TecnicosController.class).viewMisVisitas(ctx), TipoRol.TECNICO);


        // servicio externo donde donar
        app.get("/donde-donar", ViewsController::dondeDonar, TipoRol.HUMANO, TipoRol.JURIDICA);
        app.post("/donde-donar", ViewsController::dondeDonarMapa, TipoRol.HUMANO, TipoRol.JURIDICA);

        // Ver colaboradores creados
        app.get("/colaboradores", ctx -> ServiceLocator.instanceOf(UsuariosController.class).showUsuarios(ctx), TipoRol.ADMIN);

        // obtener imagenes
        app.get("/imagenes/{nombreCarpeta}/{nombreArchivo}",ViewsController::viewImage);

        // pantallas de error
        app.get("/not-found", ViewsController::notFound);

        app.get("/bad-request", ViewsController::badRequest);
    }
}
