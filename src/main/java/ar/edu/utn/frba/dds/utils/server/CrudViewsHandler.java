package ar.edu.utn.frba.dds.utils.server;

import io.javalin.http.Context;

public interface CrudViewsHandler {

    void index(Context context);

    void show(Context context);

    void create(Context context);

    void save(Context context);

    void edit(Context context);

    void update(Context context);

    void delete(Context context);

}
