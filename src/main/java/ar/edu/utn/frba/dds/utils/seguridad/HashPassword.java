package ar.edu.utn.frba.dds.utils.seguridad;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPassword {//TODO: falta hacerle test
    public String hashPassword(String password) {
        try {
            // Crear una instancia de MessageDigest con el algoritmo SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Hashear la contraseña
            byte[] hashedPasswordBytes = md.digest(password.getBytes());

            // Convertir los bytes del hash a una cadena hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedPasswordBytes) {
                // Convertir cada byte a formato hexadecimal y añadirlo al StringBuilder
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
