package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.models.entities.ubicacion.Direccion;

public class StringToDireccion {
    public static Direccion convert(String direccion) {
        String[] partes = direccion.split("(?=\\d)", 2);

        String calle = partes[0].trim();
        String altura = partes.length > 1 ? partes[1].trim() : "";
        return Direccion.of(calle, Integer.parseInt(altura));
    }
}
