package ar.edu.utn.frba.dds.utils.seguridad;

import lombok.Getter;

@Getter
public class TieneMayuscula implements CondicionContrasenia{
    private String mensaje = "La contraseña debe tener al menos una mayuscula";
    @Override
    public boolean cumpleConCondicion(String contrasenia) {
        return contrasenia.matches(".*[A-Z].*");
    }
}
