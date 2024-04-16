package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.usuarios.Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class ValidadorDeContrasenias {
    public static boolean esValida(String contrasenia) {
        ClassLoader classLoader = Usuario.class.getClassLoader();
        URL path_contrasenias_inseguras = classLoader.getResource("textFiles/list-top-10000.txt");

        if (path_contrasenias_inseguras != null) { // Para saber si está entre las 10k más inseguras
            try (BufferedReader lector = new BufferedReader(new InputStreamReader(path_contrasenias_inseguras.openStream()))) {
                String contrasenia_insegura;
                while ((contrasenia_insegura = lector.readLine()) != null) {
                    if(contrasenia_insegura.equals(contrasenia)){
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
        if (contrasenia.length() < 8) {
            return false;
        } // Para saber si tiene al menos 8 caracteres
        else if (contrasenia.length() > 64) {
            return false;
        } // Para saber si tiene al menos 8 caracteres
        else if (!contrasenia.matches(".*[a-z].*")) {
            return false;
        } // Para saber si tiene al menos una letra minúscula
        else if (!contrasenia.matches(".*[A-Z].*")) {
            return false;
        } // Para saber si tiene al menos una letra mayúscula
        else
            if (!contrasenia.matches(".*[0-9].*")) {
            return false;
        } // Para saber si tiene al menos un número
        else return contrasenia.matches(".*[!@#$%^&*].*");
        // Si tiene caracter especial, devuelve true xq ya evaluo el resto y dieron tru. Si no tiene devuelve false
    }
}
