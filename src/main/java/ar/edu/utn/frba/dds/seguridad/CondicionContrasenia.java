package ar.edu.utn.frba.dds.seguridad;

import java.io.IOException;

public interface CondicionContrasenia {

    public boolean cumpleConCondicion(String contrasenia) throws IOException;
}
