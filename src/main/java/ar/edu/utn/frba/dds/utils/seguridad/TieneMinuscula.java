package ar.edu.utn.frba.dds.utils.seguridad;

import lombok.Getter;

@Getter
public class TieneMinuscula implements CondicionContrasenia{
    private String mensaje = "La contraseña debe tener al menos una minuscula";
    @Override
    public boolean cumpleConCondicion(String contrasenia) {
        return contrasenia.matches(".*[a-z].*");
    }
}
