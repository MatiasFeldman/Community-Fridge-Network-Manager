package domain.usuarios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Usuario {
    private String user;
    private String password;

    public Usuario(String user, String password) throws ContraseñaInvalidaException {
        this.user = user;
        if(!esContraseniaSegura(password)){
            throw new ContraseñaInvalidaException("La contraseña no es segura");
        }
        this.password = password;
    }

    private boolean esContraseniaSegura(String password) {
        ClassLoader classLoader = Usuario.class.getClassLoader();
        URL path_contrasenias_inseguras = classLoader.getResource("textFiles/list-top-10000.txt");

        if (path_contrasenias_inseguras != null) { // Para saber si está entre las 10k más inseguras
            try (BufferedReader lector = new BufferedReader(new InputStreamReader(path_contrasenias_inseguras.openStream()))) {
                String contrasenia_insegura;
                while ((contrasenia_insegura = lector.readLine()) != null) {
                    if(contrasenia_insegura.equals(password)){
                        return false;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else{
            System.out.println("No se encontró el archivo de las 10 mil contrasenias más inseguras. Revisar el path");
            return false;
        }

        if (password.length() < 8) {
            return false;
        } // Para saber si tiene al menos 8 caracteres
        
        return true;
    }

    public static class ContraseñaInvalidaException extends Exception {
        public ContraseñaInvalidaException(String message) {
            super(message);
        }
    }
}
