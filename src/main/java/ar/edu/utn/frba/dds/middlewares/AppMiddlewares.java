package ar.edu.utn.frba.dds.middlewares;

import io.javalin.Javalin;

import java.util.Arrays;

public class AppMiddlewares {
    private IMiddleware[] middlewares = new IMiddleware[]{
            new AuthMiddleware()
    };

    public static void applyMiddlewares(Javalin app) {
        Arrays.stream(new AppMiddlewares().middlewares).toList().forEach(middleware -> middleware.apply(app));
    }
}
