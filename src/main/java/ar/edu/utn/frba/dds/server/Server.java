package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.middlewares.AppMiddlewares;
import ar.edu.utn.frba.dds.models.entities.helpers.conversor_json.ConversorJSON;
import ar.edu.utn.frba.dds.server.handlers.AppHandlers;
import ar.edu.utn.frba.dds.utils.DDMetricsUtils;
import ar.edu.utn.frba.dds.utils.server.Initializer;
import ar.edu.utn.frba.dds.utils.server.JavalinRenderer;
import ar.edu.utn.frba.dds.utils.server.PrettyProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;
import io.javalin.micrometer.MicrometerPlugin;

import java.io.IOException;
import java.util.function.Consumer;

public class Server {
    private static Javalin app = null;

    public static Javalin app() {
        if (app == null)
            throw new RuntimeException("App no inicializada");
        return app;
    }

    public static void init() {
        if (app == null) {
            Integer port = Integer.parseInt(PrettyProperties.getInstance().propertyFromName("server_port"));
            app = Javalin.create(config()).start(port);

            AppMiddlewares.applyMiddlewares(app);
            AppHandlers.applyHandlers(app);
            Router.init(app);

            if (Boolean.parseBoolean(PrettyProperties.getInstance().propertyFromName("dev_mode"))) {
                Initializer.init();
            }
        }
    }

    private static Consumer<JavalinConfig> config() {
        final var registry = DDMetricsUtils.getInstance().getRegistry();

        final var micrometerPlugin = new MicrometerPlugin(config -> config.registry = registry);
        return config -> {
            config.registerPlugin(micrometerPlugin);
            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/";
                staticFiles.directory = "/public";
            });

            config.fileRenderer(new JavalinRenderer().register("hbs", (path, model, context) -> {
                Handlebars handlebars = new Handlebars();
                Template template = null;

                // Registrar el helper 'json' usando ConversorJSON
                handlebars.registerHelper("json", (contextObject, options) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        // Serializar el objeto Java a JSON
                        return mapper.writeValueAsString(contextObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return "{}"; // Retornar JSON vacío en caso de error
                    }
                });

                // Registrar el helper 'or'
                handlebars.registerHelper("or", (value, options) -> {
                    for (int i = 0; i < options.params.length; i++) {
                        if (value != null && value.equals(options.params[i])) {
                            return true;
                        }
                    }
                    return false;
                });

                // Registrar el helper 'isEqual'
                handlebars.registerHelper("isEqual", new Helper<Object>() {
                    @Override
                    public Object apply(Object value1, Options options) throws IOException {
                        Object value2 = options.param(0); // Primer parámetro adicional del helper
                        if (value1 != null && value2 != null) {
                            return value1.toString().equals(value2.toString());
                        }
                        return false; // Retorna false si alguno de los valores es nulo
                    }
                });

                try {
                    template = handlebars.compile(
                            "templates/" + path.replace(".hbs", ""));
                    return template.apply(model);
                } catch (IOException e) {
                    e.printStackTrace();
                    context.status(HttpStatus.NOT_FOUND);
                    return "No se encuentra la página indicada...";
                }
            }));
        };
    }
}
