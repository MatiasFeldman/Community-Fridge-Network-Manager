package ar.edu.utn.frba.dds.utils.seguridad;

import lombok.SneakyThrows;

import java.io.IOException;

public interface CondicionContrasenia {

    public boolean cumpleConCondicion(String contrasenia);
    public String getMensaje();
}
