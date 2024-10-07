package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.controllers.*;
import ar.edu.utn.frba.dds.dtos.heladeras.HeladeraOutputDTO;
import ar.edu.utn.frba.dds.models.entities.heladeras_y_viandas.Heladera;
import ar.edu.utn.frba.dds.models.repositories.heladeras.HeladerasRepository;
import ar.edu.utn.frba.dds.services.service_locator.ServiceLocator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;


public class Router {
    public static void init(Javalin app) {
        app.get("/", ViewsController::landing);

        app.get("/colaborar", ViewsController::colaborar);

        // app.get("/colaborar/donar-dinero", ViewsController::formDonarDinero);
        app.get("/colaborar/donar-dinero", ctx ->{
            ctx.attribute("rolesRequeridos", Arrays.asList("ADMIN", "HUMANO"));
            ViewsController.formDonarDinero(ctx);
        });

        app.get("/colaborar/distribuir-viandas", ViewsController::formDistribuirViandas);

        app.get("/colaborar/donar-viandas", ViewsController::formDonarViandas);

        app.get("/colaborar/heladera-a-cargo", ViewsController::formHeladeraACargo);

        app.get("/colaborar/registro-persona-vulnerable", ViewsController::formRegistroPersonaVulnerable);

        app.get("/colaborar/ofertar", ViewsController::formRegistrarOferta);

        app.get("/heladeras/suscribirse", ctx -> ServiceLocator.instanceOf(HeladerasController.class).suscribirseAHeladeras(ctx));

        app.get("/registro/humano", ctx -> ServiceLocator.instanceOf(HumanosController.class).formRegistroHumano(ctx));

        app.post("/registro/humano", ctx -> ServiceLocator.instanceOf(HumanosController.class).save(ctx));

        app.get("/registro/juridica", ServiceLocator.instanceOf(JuridicasController.class)::create);

        //app.post("/registro/juridica", ServiceLocator.instanceOf(JuridicasController.class)::save);

        app.get("/heladeras/reportar-falla-tecnica", ViewsController::formFallaTecnica);

        app.get("/heladeras/reportar-falla-tecnica/{id}", ctx -> ServiceLocator.instanceOf(HeladerasController.class).reporteFallaTecnicaView(ctx));

        app.get("/not-found", ViewsController::notFound);

        app.get("/bad-request", ViewsController::badRequest);

        app.post("/heladeras/reportar-falla-tecnica", ctx -> ServiceLocator.instanceOf(HeladerasController.class).registrarFallaTecnica(ctx));

        app.get("/registro/modificar-registro-humano", ctx -> ServiceLocator.instanceOf(HumanosController.class).camposFormHumano(ctx));

        app.get("/heladeras/modificar", ctx -> ServiceLocator.instanceOf(HeladerasController.class).editarHeladera(ctx));

        app.delete("/heladeras/modificar", ctx -> ServiceLocator.instanceOf(HeladerasController.class).eliminarHeladera(ctx));

        app.put("/heladeras/modificar", ctx -> ServiceLocator.instanceOf(HeladerasController.class).modificarEstadoHeladera(ctx));

        app.post("/heladeras/modificar", ctx -> ServiceLocator.instanceOf(HeladerasController.class).actualizarHeladera(ctx));


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
        });


        app.get("/colaborar/carga-csv", ViewsController::cargaCsv);

        app.get("/login", ViewsController::formLogin);
        app.post("/login", ctx -> ServiceLocator.instanceOf(UsuariosController.class).handleLogin(ctx));

        app.post("/logout", ctx -> ServiceLocator.instanceOf(UsuariosController.class).handleLogout(ctx));

        app.get("/registro", ViewsController::formRegistro);

        app.get("/heladeras/nueva", ctx -> ServiceLocator.instanceOf(HeladerasController.class).create(ctx));
        app.post("/heladeras/nueva", ctx -> ServiceLocator.instanceOf(HeladerasController.class).create(ctx));
    }
}
