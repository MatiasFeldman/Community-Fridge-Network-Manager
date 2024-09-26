package ar.edu.utn.frba.dds.utils.server;

import io.javalin.http.Context;
import io.javalin.rendering.FileRenderer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class JavalinRenderer implements FileRenderer {
    private Map<String, FileRenderer> renderers = new HashMap<>();

    public JavalinRenderer register(String extension, FileRenderer renderer) {
        renderers.put(extension, renderer);
        return this;
    }

    @NotNull
    @Override
    public String render(@NotNull String s, @NotNull Map<String, ?> map, @NotNull Context context) {
        String extension = s.substring(s.lastIndexOf(".") + 1);
        return renderers.get(extension).render(s, map, context);
    }
}
