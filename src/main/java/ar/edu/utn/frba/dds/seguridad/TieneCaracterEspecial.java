package ar.edu.utn.frba.dds.seguridad;

public class TieneCaracterEspecial implements CondicionContrasenia{
    @Override
    public boolean cumpleConCondicion(String contrasenia) {
        return contrasenia.matches(".*[!@#$%^&*()].*");
    }
}
