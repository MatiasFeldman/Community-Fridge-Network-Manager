package ar.edu.utn.frba.dds.utils.seguridad;

import lombok.Getter;

@Getter
public class TieneCaracterEspecial implements CondicionContrasenia{
    private String mensaje = "La contraseña debe tener al menos un caracter especial";
    @Override
    public boolean cumpleConCondicion(String contrasenia) {
        return contrasenia.matches(".*[!@#$%^&*()].*");
    }
}
