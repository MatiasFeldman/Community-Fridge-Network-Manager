package ar.edu.utn.frba.dds.middlewares;

import io.javalin.Javalin;

public interface IMiddleware {
    void apply(Javalin app);
}
