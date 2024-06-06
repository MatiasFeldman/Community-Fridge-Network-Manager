package ar.edu.utn.frba.dds.utils.seguridad;

import java.io.IOException;

public interface CondicionContrasenia {

    public boolean cumpleConCondicion(String contrasenia) throws IOException;
}
