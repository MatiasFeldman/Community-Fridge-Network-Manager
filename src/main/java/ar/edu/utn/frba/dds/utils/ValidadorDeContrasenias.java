package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.usuarios.Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class ValidadorDeContrasenias {


    public Boolean cumpleConLaLongitud(String contrasenia) throws ContraseniaInvalidaException{
        if(!(contrasenia.length() >= 8) || !(contrasenia.length() <= 64)) {
            throw new ContraseniaInvalidaException("La longitud de la contraseña se debe encontrar entre 8 y 64 caracteres");
        }
        return true;
    }

    public Boolean cumpleConConvencionDeCaracteres(String contrasenia) throws ContraseniaInvalidaException{
        if (!contrasenia.matches(".*[a-z].*") || !contrasenia.matches(".*[A-Z].*") || !contrasenia.matches(".*[0-9].*") || !contrasenia.matches(".*[!@#$%^&*()].*")){
            throw new ContraseniaInvalidaException("La contraseña no cumple con la convención de caracteres: Debe tener al menos una minuscula, mayuscula, numero y caracter especial");
        }
        return true;
    }

    public Boolean estaEntreLas10milMasUsadas(String contrasenia) throws ContraseniaInvalidaException{
        ClassLoader classLoader = Usuario.class.getClassLoader();
        URL path_contrasenias_inseguras = classLoader.getResource("textFiles/list-top-10000.txt");

        if (path_contrasenias_inseguras != null) { // Para saber si está entre las 10k más inseguras
            try (BufferedReader lector = new BufferedReader(new InputStreamReader(path_contrasenias_inseguras.openStream()))) {
                String contrasenia_insegura;
                while ((contrasenia_insegura = lector.readLine()) != null) {
                    if(contrasenia_insegura.equals(contrasenia)){
                        throw new ContraseniaInvalidaException("La contraseña no es segura, se encuentra entre las 10 mil contraseñas más inseguras");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else{
            System.out.println("No se encontró el archivo de las 10 mil contrasenias más inseguras. Revisar el path");
            return false;
        }
        return true;
    }

    public static boolean esValida(String contrasenia) throws ContraseniaInvalidaException {
        return new ValidadorDeContrasenias().cumpleConLaLongitud(contrasenia) &&
                new ValidadorDeContrasenias().cumpleConConvencionDeCaracteres(contrasenia) &&
                new ValidadorDeContrasenias().estaEntreLas10milMasUsadas(contrasenia);
    }

    public static class ContraseniaInvalidaException extends Exception {
        public ContraseniaInvalidaException(String message) {
            super(message);
        }
    }
}
