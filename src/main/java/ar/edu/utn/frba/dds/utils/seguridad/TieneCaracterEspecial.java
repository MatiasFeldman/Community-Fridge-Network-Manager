package ar.edu.utn.frba.dds.utils.seguridad;

public class TieneCaracterEspecial implements CondicionContrasenia{
    @Override
    public boolean cumpleConCondicion(String contrasenia) {
        return contrasenia.matches(".*[!@#$%^&*()].*");
    }
}
