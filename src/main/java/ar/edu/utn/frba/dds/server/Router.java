package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.controllers.*;
import ar.edu.utn.frba.dds.models.entities.usuarios.TipoRol;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import io.javalin.Javalin;


public class Router {
    public static void init(Javalin app) {
        app.get("/", ViewsController::landing);

        app.get("/colaborar", ViewsController::colaborar, TipoRol.ADMIN,TipoRol.HUMANO, TipoRol.JURIDICA);

        app.get("/colaborar/donar-dinero", ViewsController::formDonarDinero, TipoRol.ADMIN, TipoRol.HUMANO, TipoRol.JURIDICA);

        app.post("/colaborar/donar-dinero", ctx -> ServiceLocator.instanceOf(ContribucionesController.class).crearDonacionDeDinero(ctx), TipoRol.JURIDICA, TipoRol.HUMANO);

        app.get("/colaborar/distribuir-viandas", ViewsController::formDistribuirViandas, TipoRol.ADMIN, TipoRol.HUMANO);

        app.post("/colaborar/distribuir-viandas", ctx -> ServiceLocator.instanceOf(ContribucionesController.class).crearDistribucionDeViandas(ctx), TipoRol.HUMANO);

        app.get("/colaborar/donar-viandas", ViewsController::formDonarViandas, TipoRol.ADMIN, TipoRol.HUMANO);

        app.post("/colaborar/donar-viandas", ctx -> ServiceLocator.instanceOf(ContribucionesController.class).crearDonacionDeViandas(ctx), TipoRol.HUMANO);

        app.get("/colaborar/heladera-a-cargo", ViewsController::formHeladeraACargo, TipoRol.ADMIN, TipoRol.JURIDICA);

        app.post("/colaborar/heladera-a-cargo", ctx -> ServiceLocator.instanceOf(ContribucionesController.class).registrarHeladeraACargo(ctx), TipoRol.JURIDICA);

        app.post("/recomendar-puntos", ctx -> ServiceLocator.instanceOf(ContribucionesController.class).recomendarPuntos(ctx), TipoRol.JURIDICA);

        app.get("/colaborar/registro-persona-vulnerable", ViewsController::formRegistroPersonaVulnerable, TipoRol.ADMIN, TipoRol.HUMANO);

        app.post("/colaborar/registro-persona-vulnerable", ctx -> ServiceLocator.instanceOf(ContribucionesController.class).registrarPersonaVulnerable(ctx), TipoRol.HUMANO);

        app.get("/colaborar/ofertar", ViewsController::formRegistrarOferta, TipoRol.ADMIN, TipoRol.JURIDICA);

        app.post("/colaborar/ofertar", ctx -> ServiceLocator.instanceOf(ContribucionesController.class).registrarOferta(ctx), TipoRol.JURIDICA);

        // sin rol se pueden ver las heladeras, pero si tenes rol podes suscribirte a una heladera (el checkeo se hace en el controller)
        app.get("/heladeras", ctx -> ServiceLocator.instanceOf(HeladerasController.class).mostrarHeladeras(ctx));
        app.post("/suscribirse",ServiceLocator.instanceOf(HeladerasController.class)::suscribirse);
        app.post("/desuscribirse",ServiceLocator.instanceOf(HeladerasController.class)::desuscribirse);

        app.get("/registro/humano", ctx -> ServiceLocator.instanceOf(HumanosController.class).formRegistroHumano(ctx));

        app.post("/registro/humano", ctx -> ServiceLocator.instanceOf(HumanosController.class).save(ctx));

        app.get("/registro/juridica", ServiceLocator.instanceOf(JuridicasController.class)::create);

        app.post("/registro/juridica", ServiceLocator.instanceOf(JuridicasController.class)::save);

        app.get("/heladeras/reportar-falla-tecnica", ViewsController::formFallaTecnica, TipoRol.ADMIN,TipoRol.HUMANO, TipoRol.JURIDICA);

        app.get("/heladeras/reportar-falla-tecnica/{id}", ctx -> ServiceLocator.instanceOf(HeladerasController.class).reporteFallaTecnicaView(ctx), TipoRol.ADMIN,TipoRol.HUMANO, TipoRol.JURIDICA);

        app.get("/heladeras/{id}/reporte-falla-tecnica/nueva", ctx -> ServiceLocator.instanceOf(HeladerasController.class).reporteFallaTecnicaView(ctx), TipoRol.ADMIN,TipoRol.HUMANO, TipoRol.JURIDICA);

        app.get("/heladeras/{id}/suscripcion/nueva", ctx -> ServiceLocator.instanceOf(HeladerasController.class).suscripcionView(ctx), TipoRol.ADMIN,TipoRol.HUMANO, TipoRol.JURIDICA);

        app.get("/heladeras/incidentes",ViewsController::formAlertas,TipoRol.ADMIN,TipoRol.HUMANO, TipoRol.JURIDICA);

        app.get("/heladeras/incidentes/{id}", ctx -> ServiceLocator.instanceOf(ReportesController.class).detalleInicidenteView(ctx), TipoRol.ADMIN,TipoRol.HUMANO, TipoRol.JURIDICA);

        app.get("/not-found", ViewsController::notFound);

        app.get("/bad-request", ViewsController::badRequest);

        app.post("/heladeras/reportar-falla-tecnica", ctx -> ServiceLocator.instanceOf(HeladerasController.class).registrarFallaTecnica(ctx), TipoRol.ADMIN,TipoRol.HUMANO, TipoRol.JURIDICA);

        app.get("/registro/modificar-registro-humano", ctx -> ServiceLocator.instanceOf(HumanosController.class).camposFormHumano(ctx), TipoRol.ADMIN);

        app.get("/heladeras/modificar", ctx -> ServiceLocator.instanceOf(HeladerasController.class).editarHeladera(ctx), TipoRol.ADMIN);

        app.delete("/heladeras/modificar", ctx -> ServiceLocator.instanceOf(HeladerasController.class).eliminarHeladera(ctx), TipoRol.ADMIN);

        app.put("/heladeras/modificar", ctx -> ServiceLocator.instanceOf(HeladerasController.class).modificarEstadoHeladera(ctx), TipoRol.ADMIN);

        app.post("/heladeras/modificar", ctx -> ServiceLocator.instanceOf(HeladerasController.class).actualizarHeladera(ctx), TipoRol.ADMIN);


        app.get("/heladeras/reportes", ctx -> {
            String tipo = ctx.queryParam("tipo");
            if (tipo == null) {
                ViewsController.reportesHeladerasInicio(ctx);
            } else if (tipo.equals("todos")) {
                ServiceLocator.instanceOf(ReportesController.class).generarReporteDeTodos(ctx);
            } else if (tipo.equals("fallas")) {
                ServiceLocator.instanceOf(ReportesController.class).generarReporteDeFallas(ctx);
            } else if (tipo.equals("donaciones")) {
                ServiceLocator.instanceOf(ReportesController.class).generarReporteDeViandasDonadas(ctx);
            } else if (tipo.equals("movimiento")) {
                ServiceLocator.instanceOf(ReportesController.class).generarReporteDeMovimientoViandas(ctx);
            }
        }, TipoRol.ADMIN);


        app.get("/colaborar/carga-csv", ViewsController::formCargaMasiva, TipoRol.ADMIN, TipoRol.HUMANO);

        app.post("/colaborar/carga-csv", ctx -> ServiceLocator.instanceOf(ContribucionesController.class).cargaMasiva(ctx),TipoRol.HUMANO);

        app.get("/login", ViewsController::formLogin);
        app.post("/login", ctx -> ServiceLocator.instanceOf(UsuariosController.class).handleLogin(ctx));

        app.post("/logout", ctx -> ServiceLocator.instanceOf(UsuariosController.class).handleLogout(ctx));

        app.get("/registro", ViewsController::formRegistro);

        app.get("/perfil", ServiceLocator.instanceOf(UsuariosController.class)::handlePerfil);

        app.post("/perfil", ServiceLocator.instanceOf(UsuariosController.class)::handleUpdate);

        app.get("/heladeras/nueva", ctx -> ServiceLocator.instanceOf(HeladerasController.class).create(ctx));
        app.post("/heladeras/nueva", ctx -> ServiceLocator.instanceOf(HeladerasController.class).create(ctx));

        app.get("/ofertas",  ctx ->  ServiceLocator.instanceOf(OfertasController.class).showOfertas(ctx), TipoRol.ADMIN, TipoRol.JURIDICA,TipoRol.HUMANO);
        app.get("/ofertas/{id}",  ctx ->  ServiceLocator.instanceOf(OfertasController.class).showOferta(ctx), TipoRol.ADMIN, TipoRol.JURIDICA,TipoRol.HUMANO);
        app.post("/oferta/{id}/canjear",  ServiceLocator.instanceOf(OfertasController.class)::canjearOferta,TipoRol.ADMIN, TipoRol.JURIDICA,TipoRol.HUMANO);

        app.get("/confirmacion-colaboracion", ViewsController::confirmacionColaboracion, TipoRol.ADMIN, TipoRol.HUMANO, TipoRol.JURIDICA);

        app.get("/donde-donar", ViewsController::dondeDonar);
        app.post("/donde-donar", ViewsController::dondeDonarMapa);
    }
}
