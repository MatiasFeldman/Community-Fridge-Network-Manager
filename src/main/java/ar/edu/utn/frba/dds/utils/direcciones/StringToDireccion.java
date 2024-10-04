package ar.edu.utn.frba.dds.utils.direcciones;

import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringToDireccion {
    public static Direccion convertir(String direccion) {
        String regex = "(.*?)(\\d+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(direccion.trim());

        if (matcher.matches()) {
            String calle = matcher.group(1).trim(); // Parte de texto (calle)
            Integer altura = Integer.parseInt(matcher.group(2)); // Parte numérica (altura)

            // Crear y retornar la instancia de Direccion
            return Direccion.of(calle, altura);
        } else {
            throw new IllegalArgumentException("Formato de dirección no válido");
        }

    }
}
