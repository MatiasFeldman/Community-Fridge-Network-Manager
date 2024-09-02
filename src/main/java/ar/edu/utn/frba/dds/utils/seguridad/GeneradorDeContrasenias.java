package ar.edu.utn.frba.dds.utils.seguridad;

import java.util.Random;

public class GeneradorDeContrasenias {

    public static String generateRandomString(int length) {
        String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        Random random = new Random();

        StringBuilder sb = new StringBuilder();

        // Asegurar que la cadena contenga al menos una letra, un número y un carácter especial
        sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".charAt(random.nextInt(52)));  // Añade una letra
        sb.append("0123456789".charAt(random.nextInt(10)));  // Añade un número
        sb.append("!@#$%^&*".charAt(random.nextInt(8)));  // Añade un carácter especial

        // Completa la longitud deseada con caracteres aleatorios
        for (int i = 3; i < length; i++) {
            sb.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        return sb.toString();
    }
}
