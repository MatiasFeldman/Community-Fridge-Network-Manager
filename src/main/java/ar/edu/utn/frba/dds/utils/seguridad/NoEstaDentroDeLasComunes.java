package ar.edu.utn.frba.dds.utils.seguridad;

import ar.edu.utn.frba.dds.exceptions.ArchivoDePasswordsNoEncontradoException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class NoEstaDentroDeLasComunes implements CondicionContrasenia {

    @Override
    public boolean cumpleConCondicion(String contrasenia) throws IOException {
        URL path_contrasenias_inseguras = NoEstaDentroDeLasComunes.class.getClassLoader().getResource("textFiles/list-top-10000.txt");
        if (path_contrasenias_inseguras != null) { // Para saber si está entre las 10k más inseguras
            BufferedReader lector = new BufferedReader(new InputStreamReader(path_contrasenias_inseguras.openStream()));
            String contrasenia_insegura;
            while ((contrasenia_insegura = lector.readLine()) != null) {
                if (contrasenia_insegura.equals(contrasenia)) {
                    return false;
                }
            }

        } else {
            throw new ArchivoDePasswordsNoEncontradoException("No se encontró el archivo de contraseñas inseguras");
        }
        return true;

    }
}
