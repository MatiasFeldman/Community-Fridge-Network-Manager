package ar.edu.utn.frba.dds.utils.seguridad;

import lombok.Getter;

@Getter
public class TieneNumero implements CondicionContrasenia{
    private String mensaje = "La contraseña debe tener al menos un numero";
    @Override
    public boolean cumpleConCondicion(String contrasenia) {
        return contrasenia.matches(".*[0-9].*");
    }
}
